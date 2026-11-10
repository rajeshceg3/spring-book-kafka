# Feature map - spring-book-kafka

| Feature | Entry point | Proven by |
| --- | --- | --- |
| Stream pipeline: filter out "badword", uppercase, forward to output-topic | `KafkaStreamsConfig.kafkaStream` | KafkaStreamsTopologyTest (broker-free TopologyTestDriver) |
| Application boots with streams wired | `DemoApplication` | DemoApplicationTests (context load), boot smoke |
| Message production API | `KafkaProducerService.sendMessage` | Covered by context wiring |

The automated proof lives in `.cursor/skills/verify-spring-book-kafka/`.
