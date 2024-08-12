package home;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.dockerjava.api.command.LogContainerCmd;
import home.entity.DsetEntity;
import home.model.TechnicalMetadataModel;
import home.model.TechnicalMetadataModel.TechnicalMetadata;
import home.model.TechnicalMetadataModel.TechnicalMetadata.ApplicationCatalog;
import home.model.TechnicalMetadataModel.TechnicalMetadata.ApplicationCatalog.DataStore;
import home.model.TechnicalMetadataModel.TechnicalMetadata.ApplicationCatalog.DataStore.DataSet;
import home.model.TechnicalMetadataModel.TechnicalMetadata.ApplicationCatalog.DataStore.DataSet.PhysicalDataElement;
import home.repository.ApplRepository;
import home.repository.DsetRepository;
import home.repository.PdeRepository;
import home.service.DatabaseService;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import oracle.jdbc.OracleDriver;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.UseMainMethod;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.output.FrameConsumerResultCallback;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.WaitingConsumer;
import org.testcontainers.containers.wait.strategy.AbstractWaitStrategy;

@SpringBootTest(
    useMainMethod = UseMainMethod.NEVER,
    properties = {"spring.jpa.properties.hibernate.format_sql=true"})
class KafkaOracleTest {
  static final String TOPIC_NAME = "TECHNICAL_METADATA";
  static final String ERROR_TOPIC_NAME = "TECHNICAL_METADATA_ERROR";

  private static OracleContainer oracle;
  //  private static PostgreSQLContainer postgres;
  private static DockerComposeContainer kafka;

  @Autowired private ApplRepository applRepo;
  @Autowired private DsetRepository dsetRepo;
  @Autowired private PdeRepository pdeRepo;
  @Autowired private DatabaseService dbService;
  @Autowired private KafkaConfiguration c;

  @Autowired
  @Qualifier(Application.COMMON_KAFKA_PROPERTIES)
  //  private Map<String, Object> commonKafkaProperties; // doesn't work
  private Map commonKafkaProperties;

  @Autowired private ApplicationContext applicationContext;

  @TestConfiguration
  static class Configuration {}

  @BeforeAll
  static void beforeAll() throws IOException, SQLException {
    final String user = "user1";
    final String password = "user1pass";
    oracle =
        new OracleContainer("gvenzl/oracle-xe:21-slim").withUsername(user).withPassword(password);
    oracle.start();
    System.out.printf("Jdbc url: %s\n", oracle.getJdbcUrl());
    System.setProperty("spring.datasource.driver-class-name", OracleDriver.class.getName());
    System.setProperty("spring.datasource.url", oracle.getJdbcUrl());
    // TODO I can't find an Oracle docker image that is supported by flyway. So I need to run the
    //   sql statements with my code
    System.setProperty("spring.flyway.enabled", "false");
    System.setProperty("jdbc.drivers", OracleDriver.class.getName());
    try (var connection = DriverManager.getConnection(oracle.getJdbcUrl(), user, password)) {
      var statement = connection.createStatement();
      StringBuilder sqlStatement = new StringBuilder();
      for (String line :
          Files.readAllLines(
              Path.of("src/test/resources/kafkaOracleTest/db-migration/V1__create-tables.sql"))) {
        if (line.isEmpty()) {
          continue;
        }
        line = line.stripTrailing();
        if (line.endsWith(";")) {
          // the sql statement is not allowed to have semicolon
          sqlStatement.append('\n').append(line, 0, line.length() - 1);
          statement.addBatch(sqlStatement.toString());
          sqlStatement.setLength(0);
        } else {
          sqlStatement.append('\n').append(line);
        }
      }

      statement.executeBatch();
    }
    /*
        postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
                .withUsername(user)
                .withPassword(password);
        postgres.start();
        System.setProperty("spring.datasource.driver-class-name", Driver.class.getName());
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.flyway.locations", "classpath:/kafkaPostgresTest/db-migration");
    */
    System.setProperty("spring.datasource.username", user);
    System.setProperty("spring.datasource.password", password);
    System.setProperty("spring.datasource.hikari.schema", user);

    kafka =
        new DockerComposeContainer(new File("src/test/resources/kafka-compose.yml"))
            .withExposedService("broker", 9092)
            .withExposedService("schema-registry", 8081)
            .waitingFor(
                "broker",
                new MyLogMessageWaitStrategy(".*Leader imbalance ratio for broker 1 is 0.0.*"));
    kafka.start();
    String broker =
        kafka.getServiceHost("broker", 9092) + ":" + kafka.getServicePort("broker", 9092);
    System.setProperty("kafka.bootstrapServers", broker);
    String schemaRegistry =
        kafka.getServiceHost("schema-registry", 8081)
            + ":"
            + kafka.getServicePort("schema-registry", 8081);
    System.setProperty("kafka.useSSL", "false");
    System.setProperty("kafka.schemaRegistry", schemaRegistry);
    System.setProperty("kafka.topicName", TOPIC_NAME);
    System.setProperty("kafka.errorTopicName", ERROR_TOPIC_NAME);
    // create a topic
    var adminClient =
        AdminClient.create(
            Map.ofEntries(Map.entry(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, broker)));
    adminClient.createTopics(List.of(new NewTopic(TOPIC_NAME, Optional.of(4), Optional.empty())));
    adminClient.createTopics(
        List.of(new NewTopic(ERROR_TOPIC_NAME, Optional.of(4), Optional.empty())));
  }

  @AfterAll
  static void afterAll() {
    kafka.stop();
    oracle.stop();
    //    postgres.stop();
  }

  @Test
  void test() throws Exception {
    var processor = new Processor(c, commonKafkaProperties, dbService);
    var thread = new Thread(processor);
    thread.start();

    var objectMapper =
        new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

    Map<String, Object> producerProps = new HashMap<>(commonKafkaProperties);
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    producerProps.put(ProducerConfig.ACKS_CONFIG, "all");
    KafkaProducer<String, byte[]> producer = new KafkaProducer<>(producerProps);
    producer
        .send(
            new ProducerRecord<>(
                c.topicName(),
                null,
                objectMapper.writeValueAsBytes(
                    new TechnicalMetadataModel(
                        1,
                        "client1",
                        "client1@mail.com",
                        new TechnicalMetadata(
                            new ApplicationCatalog(
                                "datasource-id",
                                "application-id",
                                "physical server name",
                                "source-application-id",
                                "source appl id type text",
                                "appl-sys-1",
                                "appl system type name",
                                "datastore platform code",
                                "datastore platform name",
                                "https://my.url.com",
                                new DataStore(
                                    "datastore name",
                                    "datastore description",
                                    "datastore location name",
                                    new DataSet(
                                        "dataset name",
                                        "source datastore name",
                                        "dataset description",
                                        "dataset type name",
                                        "partition column name",
                                        "usergroup type tyext",
                                        "usergroup text",
                                        "replica clusters text",
                                        Instant.now().minusSeconds(9),
                                        List.of(
                                            new PhysicalDataElement(
                                                "physical data element name",
                                                "physical data element data type",
                                                "physical data element description",
                                                "physical data element format",
                                                "security level name",
                                                "external security classification text",
                                                "pii text",
                                                "business element name",
                                                4L,
                                                5L,
                                                6L,
                                                true,
                                                false,
                                                true,
                                                false,
                                                true,
                                                false),
                                            new PhysicalDataElement(
                                                "physical DE name",
                                                "physical DE data type",
                                                "physical DE description",
                                                "physical DE format",
                                                "SEC level name",
                                                "external SEC classification text",
                                                "PII example",
                                                "BUS element name",
                                                -1L,
                                                -2L,
                                                -3L,
                                                true,
                                                true,
                                                true,
                                                false,
                                                false,
                                                false))))))))))
        .get();

    Awaitility.waitAtMost(Duration.ofSeconds(9))
        .pollInterval(Duration.ofSeconds(1))
        .until(
            () -> {
              return applRepo.findAll().size() == 1
                  && dsetRepo.findAll().size() == 1
                  && pdeRepo.findAll().size() == 2;
            });

    processor.setRunning(false);
  }

  @Test
  void testRepositories() {
    dsetRepo.save(new DsetEntity()
        .setApiMtdatDsetId(Application.generateV7Uuid())
        .setApiMtdatApplId(Application.generateV7Uuid())
        .setMsgTypeNum(1)
        .setDatStorNm("store name")
        .setPhysSvrNm("physical server name")
        .setDatStorLocNm("datastore location name")
        .setDatStorPltfmNm("datastore platform name")
        .setUrl("https://my.url.com")
        .setApplSysTypeCd("appl-sys-1")
        .setApplSysTypeNm("appl system type name")
        .setDatStorPltfmCd("datstore platform code")
        .setDatSetNm("dataset name")
        .setDatSetDesc("blah")
        .setDatSetTypeNm("dataset type name")
        .setDatSetCrteDt(LocalDateTime.now().minus(Duration.ofSeconds(9)))
        .setPrtnColNm("partition col name")
        .setUsrGrpTypeTxt("usergroup type text")
        .setUsrGrpTxt("usergroup text")
        .setReplicaClustersTxt("replica clusters text")
        .setLdInd('N')
        .setRunId("TMDL-2024-08-09T22:02:50-0400")
        .setCrteById("client1")
        .setUpdtById("client1")
        .setDatSrcIngstSysNm("data source ingestion system name"));
    assertEquals(dsetRepo.findAll().size(), 1);
  }
}

class MyLogMessageWaitStrategy extends AbstractWaitStrategy {
  private static final Logger LOG = LoggerFactory.getLogger(MyLogMessageWaitStrategy.class);
  private final String regex;
  private final int times;

  @Override
  protected void waitUntilReady() {
    WaitingConsumer waitingConsumer = new WaitingConsumer();

    LogContainerCmd cmd =
        waitStrategyTarget
            .getDockerClient()
            .logContainerCmd(waitStrategyTarget.getContainerId())
            .withFollowStream(true)
            .withSince(0)
            .withStdOut(true)
            .withStdErr(true);

    try (FrameConsumerResultCallback callback = new FrameConsumerResultCallback()) {
      callback.addConsumer(OutputFrame.OutputType.STDOUT, waitingConsumer);
      callback.addConsumer(OutputFrame.OutputType.STDERR, waitingConsumer);

      cmd.exec(callback);

      Predicate<OutputFrame> waitPredicate =
          outputFrame -> {
            // (?s) enables line terminator matching (equivalent to Pattern.DOTALL)
            String logMessage = outputFrame.getUtf8String();
            LOG.info("Docker container: {}", logMessage);
            return logMessage.matches("(?s)" + regex);
          };
    } catch (IOException ioe) {
      throw new UncheckedIOException(ioe);
    }
  }

  public MyLogMessageWaitStrategy(String regex, int times) {
    this.regex = regex;
    this.times = times;
  }

  public MyLogMessageWaitStrategy(int times) {
    this.regex = "";
    this.times = times;
  }

  public MyLogMessageWaitStrategy(String regex) {
    this.regex = regex;
    this.times = 1;
  }
}
