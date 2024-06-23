package home;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class MessageGettingRunnable implements Runnable {
  private final BlockingQueue<Partition_Key_Value> blockingQueue;
  private final String topic;
  private final String groupId;

  private volatile boolean running = true;

  public MessageGettingRunnable(
      BlockingQueue<Partition_Key_Value> blockingQueue, String topic, String groupId) {
    this.blockingQueue = blockingQueue;
    this.topic = topic;
    this.groupId = groupId;
  }

  @Override
  public void run() {
    Properties kafkaProperties = Main.kafkaProperties();
    kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    try (KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(kafkaProperties)) {
      kafkaConsumer.subscribe(List.of(topic));
      while (running) {
        for (ConsumerRecord<String, String> consumerRecord :
            kafkaConsumer.poll(Duration.ofMillis(500))) {
          blockingQueue.put(
              new Partition_Key_Value(
                  consumerRecord.partition(), consumerRecord.key(), consumerRecord.value()));
        }
        if (blockingQueue.size() > 99_999) { // nobody reads messages from this queue
          running = false;
        }
      }
    } catch (Throwable t) {
      running = false;
    }
  }

  public void stop() {
    running = false;
  }

  public boolean isRunning() {
    return running;
  }
}
