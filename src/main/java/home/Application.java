package home;

import home.service.DatabaseService;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.kafka.clients.CommonClientConfigs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {
  public static final String COMMON_KAFKA_PROPERTIES = "commonKafkaPropertiesX";
  private static final long JVM_UNIQUE_NUMBER = new SecureRandom().nextLong();

  public static void main(String[] args) throws InterruptedException {
    var applicationContext = SpringApplication.run(Application.class, args);

    var thread =
        new Thread(
            new Processor(
                applicationContext.getBean(KafkaConfiguration.class),
                applicationContext.getBean(COMMON_KAFKA_PROPERTIES, Map.class),
                applicationContext.getBean(DatabaseService.class)));
    thread.start();
    thread.join();
  }

  public static String generateV7Uuid() {
    final long MASK_12 = 0x0000_0000_0000_0fffL;
    final long time = System.currentTimeMillis();

    final long msb =
        (time << 16) | ((ThreadLocalRandom.current().nextLong() ^ JVM_UNIQUE_NUMBER) & MASK_12);
    final long lsb = ThreadLocalRandom.current().nextLong() ^ JVM_UNIQUE_NUMBER;
    return new UUID(msb, lsb).toString();
  }

  @Bean(COMMON_KAFKA_PROPERTIES)
  public Map<String, Object> commonKafkaProperties(KafkaConfiguration c) {
    Map<String, Object> result = new HashMap<>();
    result.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, c.bootstrapServers());
    if (c.useSSL()) {
      throw new UnsupportedOperationException("already wrote this logic, so copy it here");
    }
    return result;
  }
}
