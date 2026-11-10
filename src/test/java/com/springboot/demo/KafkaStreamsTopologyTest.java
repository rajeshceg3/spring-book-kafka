package com.springboot.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.Test;

class KafkaStreamsTopologyTest {

    @Test
    void filtersBadwordAndUppercases() {
        StreamsBuilder builder = new StreamsBuilder();
        new KafkaStreamsConfig().kafkaStream(builder);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "topology-test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        try (TopologyTestDriver driver = new TopologyTestDriver(builder.build(), props)) {
            TestInputTopic<String, String> in = driver.createInputTopic("input-topic",
                    Serdes.String().serializer(), Serdes.String().serializer());
            TestOutputTopic<String, String> out = driver.createOutputTopic("output-topic",
                    Serdes.String().deserializer(), Serdes.String().deserializer());

            in.pipeInput("k1", "hello world");
            in.pipeInput("k2", "this has badword inside");

            List<KeyValue<String, String>> results = out.readKeyValuesToList();
            assertEquals(1, results.size());
            assertEquals(KeyValue.pair("k1", "HELLO WORLD"), results.get(0));
        }
    }
}
