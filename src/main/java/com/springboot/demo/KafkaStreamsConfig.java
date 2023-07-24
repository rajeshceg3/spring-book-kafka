package com.springboot.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

@Configuration
public class KafkaStreamsConfig {

    @Bean
    public StreamsBuilderFactoryBean streamsBuilder() {
        return new StreamsBuilderFactoryBean();
    }

    @Bean
    public KStream<String, String> kafkaStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("input-topic");
        
        // Perform processing steps on the stream
        stream.filter((key, value) -> !value.contains("badword"))
              .mapValues(value -> value.toUpperCase())
              .to("output-topic");
        
        return stream;
    }
}
