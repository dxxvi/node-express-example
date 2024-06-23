package home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@SpringBootApplication
public class Main {
  private static final Logger log = LoggerFactory.getLogger(Main.class);
  private static final Random random = new Random();
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final List<Topic_GroupId> topic_groupIds = new CopyOnWriteArrayList<>();

  public static void main(String[] args) {
    var springApplication = new SpringApplication(Main.class);
    springApplication.setBannerMode(Mode.OFF);
    springApplication.run(args);
  }

  @GetMapping(path = "/send1/{key}")
  public ResponseEntity<String> send1(@PathVariable(name = "key") String key) throws Throwable {
    Thread.sleep(2000);
    if (random.nextBoolean()) {
      return ResponseEntity.ok("");
    }
    return ResponseEntity.internalServerError().body("");
  }

  /**
   * This is very inefficient (in the browser, it takes 4-6s to get a message): we have to connect
   * to the kafka cluster every time to get 1 message.
   * Idea: having a thread (identified by the topic + groupId) polling messages and putting them in
   * a blocking queue (we must be able to find the queue if we have the thread). This method will
   * check if that thread exists. If not, it will create it.
   */
  @GetMapping(path = "/get-message/{topic}/group-id/{group-id}")
  public DeferredResult<String> getMessage(
      @PathVariable(name = "topic") String topic, @PathVariable(name = "group-id") String groupId) {
    // Not sure if we really run in a virtual thread as the thread name is `tomcat-handler-...`

    Optional<Topic_GroupId> topic_groupIdOptional =
        topic_groupIds.stream()
            .filter(x -> x.topic().equals(topic) && x.groupId().equals(groupId))
            .findAny();
    if (topic_groupIdOptional.isEmpty()) {
      var blockingQueue = new LinkedBlockingDeque<Partition_Key_Value>();
      var messageGettingRunnable = new MessageGettingRunnable(blockingQueue, topic, groupId);
      Thread.ofVirtual().name(topic + "-" + groupId).start(messageGettingRunnable);
      topic_groupIdOptional =
          Optional.of(new Topic_GroupId(topic, groupId, blockingQueue, messageGettingRunnable));
      topic_groupIds.add(topic_groupIdOptional.get());
    }

    DeferredResult<String> result = new DeferredResult<>();

    try {
      var partition_key_value = topic_groupIdOptional.get().queue().poll(9, TimeUnit.DAYS);
      if (partition_key_value != null) {
        try {
          result.setResult(
              objectMapper.writeValueAsString(
                  Map.ofEntries(
                      Map.entry("partition", partition_key_value.partition()),
                      Map.entry("key", partition_key_value.key()),
                      Map.entry("value", partition_key_value.value()))));
        } catch (JsonProcessingException e) {
          result.setResult(
              """
              {
                "partition": %d,
                "key": "%s",
                "value": "%s"
              }"""
                  .formatted(
                      partition_key_value.partition(),
                      partition_key_value.key(),
                      e.getMessage().replace('"', ' ')));
        }
      }
    } catch (InterruptedException iex) {
      result.setErrorResult(iex.getMessage());
    }

    return result;
  }

  public static Properties kafkaProperties() {
    final Properties props = new Properties();
    props.put(
        CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
        "pkc-419q3.us-east4.gcp.confluent.cloud:9092");
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

  public static String toTimestampString(long ms) {
    try {
      return new Date(ms).toString();
    } catch (Throwable t) {
      return Long.toString(ms);
    }
  }
}
