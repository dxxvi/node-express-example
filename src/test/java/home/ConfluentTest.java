package home;

import static java.util.UUID.randomUUID;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Produced;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ConfluentTest {
  private static final Logger LOG = LoggerFactory.getLogger(ConfluentTest.class);

  @Test
  void testGetLatestOffset() throws Throwable {
    var properties = Main.kafkaProperties();
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, randomUUID().toString());
    try (var kafkaConsumer = new KafkaConsumer<byte[], byte[]>(properties)) {
      List<TopicPartition> topicPartitions =
          kafkaConsumer.partitionsFor("GAME_SCORE").stream()
              .map(
                  partitionInfo ->
                      new TopicPartition(partitionInfo.topic(), partitionInfo.partition()))
              .toList();
      kafkaConsumer
          .endOffsets(topicPartitions)
          .forEach(
              (topicPartition, offset) ->
                  LOG.debug(
                      "Topic: {}, partititon: {}, offset: {}",
                      topicPartition.topic(),
                      topicPartition.partition(),
                      offset));
    }
  }

  /**
   * partition 0=[f, g, n, q, r, u, w, y]
   *           1=[a, c, h, l, t, x]
   *           2=[b, d, e, i, j, k, m, o, p, s, v, z]
   */
  @Test
  void testProducing() throws Throwable {
    final String TOPIC_NAME = "GAME_SCORE";
    try (var kafkaProducer =
        new KafkaProducer<>(
            Main.kafkaProperties(), new StringSerializer(), new StringSerializer())) {
      AtomicInteger ai = new AtomicInteger();
      Consumer<String> consumer =
          s -> {
            try {
              RecordMetadata rmd =
                  kafkaProducer
                      .send(
                          new ProducerRecord<>(
                              TOPIC_NAME,
                              s + s,
                              Integer.toString(ai.getAndIncrement()) + randomUUID()))
                      .get();
              LOG.info(
                  "A ({}, {}) is sent to topic {}, partition {}, offset {} at {}",
                  s,
                  "",
                  rmd.topic(),
                  rmd.partition(),
                  rmd.offset(),
                  Main.toTimestampString(rmd.timestamp()));
            } catch (InterruptedException | ExecutionException e) {
              throw new RuntimeException(e);
            }
          };

      for (char c = 'a'; c <= 'z'; c++) consumer.accept(Character.toString(c));
    }
  }

  @Test
  void testSimpleStream() throws Throwable {
    final String TOPIC_NAME = "GAME_SCORE";
    var streamsBuilder = new StreamsBuilder();

    KStream<String, String> kStream =
        streamsBuilder.stream(TOPIC_NAME, Consumed.with(new Serdes.StringSerde(), Serdes.String()));
    KTable<String, Long> _ =
        kStream
            .groupByKey() // KGroupedStream<String, String>
            .count(Named.as("hm"));

    Properties kafkaProps = Main.kafkaProperties();
    kafkaProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-stream");
    var topology = streamsBuilder.build();
    var kafkaStreams = new KafkaStreams(topology, kafkaProps);
    Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    kafkaStreams.start();

    Thread.sleep(99_999);
  }

  @Test
  void testKTable() throws Throwable {
    final String TOPIC_NAME = "GAME_SCORE";
    var streamsBuilder = new StreamsBuilder();

    var kTable =
        streamsBuilder.table(
            TOPIC_NAME,
            Consumed.with(Serdes.String(), Serdes.String()),
            Materialized.as(TOPIC_NAME + "_store"));
    kTable
        .toStream()
        .peek((k, v) -> LOG.debug("After toStream: {} -> {}", k, v))
        .to("GAME_SCORE_2", Produced.with(Serdes.String(), Serdes.String()));

    var topology = streamsBuilder.build();
    Properties kafkaProps = Main.kafkaProperties();
    kafkaProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-ktable");
    var kafkaStreams = new KafkaStreams(topology, kafkaProps);
    Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
    kafkaStreams.start();
    Thread.sleep(99_999);
  }

  @Test
  void test() {
    Map<Long, List<String>> map =
        List.of("a", "b", "c", "b", "a").stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(
                    Entry::getValue,
                    entry -> List.of(entry.getKey()),
                    (list1, list2) -> {
                      var list = new ArrayList<>(list1);
                      list.addAll(list2);
                      return list;
                    }));
    System.out.println(map);
  }
}
