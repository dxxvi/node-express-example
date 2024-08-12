package home;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kafka")
public record KafkaConfiguration(
    String bootstrapServers,
    String consumerGroup,
    String errorTopicName,
    String schemaRegistry,
    SSL ssl,
    String topicName,
    boolean useSSL) {
  public record SSL(String keyPassword, Store keyStore, Store trustStore) {
    public record Store(String location, String password, String type) {}
  }
}
