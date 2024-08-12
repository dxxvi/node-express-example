package home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import home.model.TechnicalMetadataModel;
import home.service.DatabaseService;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processor implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(Processor.class);
  private static final ObjectMapper objectMapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  private final Map<String, Object> consumerProps;
  private final String topicName;
  private final String errorTopicName;
  private final KafkaProducer<String, byte[]> errorTopicProducer;
  private final DatabaseService dbService;

  private volatile boolean running = true; // so that we can stop in unit tests

  public Processor(
      KafkaConfiguration c, Map<String, Object> commonKafkaProperties, DatabaseService dbService) {
    consumerProps = new HashMap<>(commonKafkaProperties);
    consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, c.consumerGroup());
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    consumerProps.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);

    Map<String, Object> producerProps = new HashMap<>(commonKafkaProperties);
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    producerProps.put(ProducerConfig.ACKS_CONFIG, "all");
    this.errorTopicProducer = new KafkaProducer<>(producerProps);

    this.topicName = c.topicName();
    this.errorTopicName = c.errorTopicName();
    this.dbService = dbService;
  }

  @Override
  public void run() {
    while (running) {
      try {
        doRun();
      } catch (Throwable t) {
        log.error("How can we come here", t);
      }
    }
  }

  private void doRun() {
    try (KafkaConsumer<String, byte[]> consumer = new KafkaConsumer<>(consumerProps)) {
      consumer.subscribe(Set.of(topicName));

      while (running) {
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));
        if (records.isEmpty()) continue;
        if (records.iterator().hasNext() && records.count() > 1)
          throw new IllegalStateException(
              "Wrong configuration: " + records.count() + " are fetched at a time");

        ConsumerRecord<String, byte[]> record = records.iterator().next();
        int partition = record.partition();
        long offset = record.offset();
        String key = record.key();
        byte[] bytes = record.value();
        TechnicalMetadataModel model = toModel(bytes);

        if (model == null) { // unable to deserialize the byte array
          continue;
        }

        try {
          dbService.writeToDb(model);
          consumer.commitSync();
        } catch (Throwable t) {
          // TODO we won't commit the partition/offset. So, we'll receive this record again in a few
          //   ms. Need a strategy to deal with this situation.
          log.error("Unable to write to DB", t);
        }
      }
    }
  }

  /**
   * This method should not throw any exception
   * @return null if unable to deserialize bytes to a TechnicalMetadataModel object
   */
  private TechnicalMetadataModel toModel(byte[] bytes) {
    try {
      return objectMapper.readValue(bytes, TechnicalMetadataModel.class);
    } catch (Throwable t) {
      log.error("We can assume that this never happens", t);
      return null;
    }
  }

  public void setRunning(boolean running) {
    this.running = running;
  }
}
