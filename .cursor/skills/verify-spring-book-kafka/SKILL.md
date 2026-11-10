---
name: verify-spring-book-kafka
description: Verifies the spring-book-kafka Kafka Streams pipeline against its promised behavior - the Maven suite including a broker-free topology test, plus a live boot smoke - with machine-readable evidence.
---

# Verify spring-book-kafka

## Launch

```bash
# JDK 11+ and Maven required; e.g. JAVA_HOME=/path/to/jdk PATH=$JAVA_HOME/bin:$PATH
python3 .cursor/skills/verify-spring-book-kafka/verify.py
```

Exit 0 and `FAILURES=0` mean green. No Kafka broker is needed: the
context-load test and the `TopologyTestDriver` topology test run
broker-free, and the boot smoke tolerates the streams client's
background broker retries.

## Doctor

Gates on a JDK 11+ and `mvn` being available.

## Drive

1. `mvn -B test` - context load plus the stream topology test
   (badword filtered, values uppercased, broker-free).
2. Boot smoke: `mvn spring-boot:run` on a dynamically chosen free port
   (`SERVER_PORT`); the embedded web server port must open.

## Evidence

Each run writes a fresh `EVIDENCE_DIR` with `evidence.json`.
`FAILURES=n` is the last line; the exit code mirrors it.

## Cleanup

The boot-smoke server runs in its own process group and is terminated at
the end of the run, pass or fail.

## Maintenance

When the topology or promised pipeline behavior changes, extend
`KafkaStreamsTopologyTest` and this skill's checks in the same commit.

## Known boundaries

- End-to-end produce/consume against a live broker is out of scope;
  the topology is proven with `TopologyTestDriver` instead.
- The app has no HTTP endpoints; the boot smoke proves the process
  comes up and serves its port.
