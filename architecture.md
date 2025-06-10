```mermaid
graph TD
    A[DemoApplication] --> B(KafkaProducerService)
    B -- Pushes messages to --> C[Kafka Topic: input-topic]

    subgraph Kafka Streams Pipeline (defined in KafkaStreamsConfig)
        C -- Consumed by --> D{Filter messages}
        D -- Filtered messages --> E{Map values}
        E -- Mapped messages --> F[Kafka Topic: output-topic]
    end

    A --> G(KafkaStreamsConfig)
```
