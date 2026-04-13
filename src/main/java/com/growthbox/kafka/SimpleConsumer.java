package com.growthbox.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SimpleConsumer {

    public static void main(String[] args) {

        // 1. Define the topic to consume from (same as producer)
        String topic = "my-first-topic";

        // 2. Configure consumer properties
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-first-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // 3. Create the Kafka consumer
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {

            // 4. Subscribe to the topic
            consumer.subscribe(Collections.singletonList(topic));

            System.out.println("Consumer started. Listening on topic: " + topic);
            System.out.println("Press Ctrl+C to stop.\n");

            // 5. Poll for messages continuously
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("Received -> key: " + record.key()
                            + ", value: " + record.value()
                            + " | partition: " + record.partition()
                            + ", offset: " + record.offset());
                }
            }

        } catch (Exception e) {
            System.err.println("Error consuming messages: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
