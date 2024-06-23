package home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.security.auth.SecurityProtocol;
import org.apache.kafka.common.security.plain.PlainLoginModule;
import org.apache.kafka.common.serialization.StringDeserializer;
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

  @GetMapping(path = "/get-message/{topic}/group-id/{group-id}")
  public DeferredResult<String> getMessage(
      @PathVariable(name = "topic") String topic, @PathVariable(name = "group-id") String groupId) {
    // Not sure if we really run in a virtual thread as the thread name is `tomcat-handler-...`

    Properties kafkaProperties = kafkaProperties();
    kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    //    kafkaProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);

    DeferredResult<String> result = null;

    try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaProperties)) {
      kafkaConsumer.subscribe(List.of(topic));
      while (result == null) {
        for (ConsumerRecord<String, String> consumerRecord :
            kafkaConsumer.poll(Duration.ofMillis(500))) {
          result = new DeferredResult<>();
          try {
            result.setResult(
                objectMapper.writeValueAsString(
                    Map.ofEntries(
                        Map.entry("key", consumerRecord.key()),
                        Map.entry("value", consumerRecord.value()))));
          } catch (JsonProcessingException e) {
            result.setResult(
                """
                {
                  "key": "error",
                  "value": "%s"
                }"""
                    .formatted(e.getMessage().replace('"', ' ')));
          }
          kafkaConsumer.commitSync(Map.of(
              new TopicPartition(consumerRecord.topic(), consumerRecord.partition()),
              new OffsetAndMetadata(
                  consumerRecord.offset() + 1) // offset is the next one we want to read
          ));
          break; // because we want to get only 1 message at a time
        }
      }
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
