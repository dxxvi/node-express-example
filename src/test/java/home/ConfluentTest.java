package home;

import java.util.Date;
import java.util.Properties;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConfluentTest {
  private static final Logger LOG = LoggerFactory.getLogger(ConfluentTest.class);

  @Test
  void testConnection() throws Throwable {
    try (var adminClient = AdminClient.create(kafkaProperties())) {
      adminClient.listTopics().listings().get().forEach(System.out::println);
    }
  }

  @Test
  void testProducing() throws Throwable {
    final String TOPIC_NAME = "GAME_SCORE";
    final String KEY = "key 3";
    final String VALUE = "value 3";
    try (var kafkaProducer =
        new KafkaProducer<>(kafkaProperties(), new StringSerializer(), new StringSerializer())) {
      RecordMetadata rmd = kafkaProducer.send(new ProducerRecord<>(TOPIC_NAME, KEY, VALUE)).get();
      LOG.info(
          "A ({}, {}) is sent to topic {}, partition {}, offset {} at {}",
          KEY,
          VALUE,
          rmd.topic(),
          rmd.partition(),
          rmd.offset(),
          toTimestampString(rmd.timestamp()));
    }
  }

  @Test
  void testSimpleStream() throws Throwable {
    final String TOPIC_NAME = "GAME_SCORE";
    var streamsBuilder = new StreamsBuilder();
    KStream<String, String> kStream = streamsBuilder.stream(TOPIC_NAME, Consumed.with(new Serdes.StringSerde(), new Serdes.StringSerde()));
    kStream.foreach((key, value) -> LOG.debug("(DSL) key: {}, value: {}", key, value));

    Properties kafkaProps = kafkaProperties();
    kafkaProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-stream");
    var kafkaStreams = new KafkaStreams(streamsBuilder.build(), kafkaProps);
    Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    kafkaStreams.start();

    Thread.sleep(999_999);
  }

  private Properties kafkaProperties() {
    final Properties props = new Properties();
    props.put(
        CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
        "pkc-56d1g.eastus.azure.confluent.cloud:9092");
    props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, SecurityProtocol.SASL_SSL.name());
    props.put(
        SaslConfigs.SASL_JAAS_CONFIG,
        "%s required username='%s' password='%s';"
            .formatted(
                PlainLoginModule.class.getName(),
                System.getenv("CONFLUENT_API_KEY"),
                System.getenv("CONFLUENT_API_SECRET")));
    props.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
    props.put(CommonClientConfigs.CLIENT_DNS_LOOKUP_CONFIG, "use_all_dns_ips");
    props.put(CommonClientConfigs.SESSION_TIMEOUT_MS_CONFIG, "45000");
    return props;
  }

  private static String toTimestampString(long ms) {
    try {
      return new Date(ms).toString();
    } catch (Throwable t) {
      return Long.toString(ms);
    }
  }
}
