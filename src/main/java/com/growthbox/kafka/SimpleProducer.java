package com.growthbox.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleProducer {

    public static void main(String[] args) {

        // 1. Define the Kafka topic to send messages to
        String topic = "my-first-topic";

        // 2. Configure producer properties
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 3. Create the Kafka producer
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {

            // 4. Send 10 simple string messages
            for (int i = 1; i <= 10; i++) {
                String key = "key-" + i;
                String value = "Hello Kafka - message " + i;

                ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

                // Send the record and get metadata back (synchronous send)
                RecordMetadata metadata = producer.send(record).get();

                System.out.println("Sent -> key: " + key
                        + ", value: " + value
                        + " | partition: " + metadata.partition()
                        + ", offset: " + metadata.offset());
            }

            System.out.println("All messages sent successfully.");

        } catch (Exception e) {
            System.err.println("Error sending messages: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
