# Kafka Producer & Consumer - Simple Java Application

A minimal Apache Kafka producer and consumer built with plain Java and Maven. No Spring or Spring Boot -- just the `kafka-clients` library.

## Project Structure

```
src/main/java/com/growthbox/kafka/
  SimpleProducer.java   -- Sends string messages to a Kafka topic
  SimpleConsumer.java   -- Reads string messages from a Kafka topic
```

## Prerequisites

- Java 17+
- Maven 3.6+
- Apache Kafka running on `localhost:9092`

## Dependencies

| Library       | Version |
| ------------- | ------- |
| kafka-clients | 3.7.0   |
| slf4j-simple  | 2.0.12  |

## Build

```bash
mvn clean compile
```

## Run

### 1. Create the Kafka topic (if auto-creation is not enabled)

```bash
kafka-topics.sh --create --topic my-first-topic --bootstrap-server localhost:9092
```

### 2. Start the Consumer

```bash
mvn exec:java -Dexec.mainClass="com.growthbox.kafka.SimpleConsumer"
```

The consumer subscribes to `my-first-topic` with group `my-first-consumer-group` and polls continuously. Press `Ctrl+C` to stop.

### 3. Run the Producer (in a separate terminal)

```bash
mvn exec:java -Dexec.mainClass="com.growthbox.kafka.SimpleProducer"
```

Sends 10 string messages (with keys) to `my-first-topic` and prints the partition and offset for each.

## Configuration

Both classes connect to `localhost:9092` by default. To change the broker address, update `BOOTSTRAP_SERVERS_CONFIG` in the respective Java file.

| Property          | Producer Value   | Consumer Value          |
| ----------------- | ---------------- | ----------------------- |
| Bootstrap Servers | localhost:9092   | localhost:9092          |
| Topic             | my-first-topic   | my-first-topic          |
| Key Serializer    | StringSerializer | StringDeserializer      |
| Value Serializer  | StringSerializer | StringDeserializer      |
| Group ID          | --               | my-first-consumer-group |
| Auto Offset Reset | --               | earliest                |
