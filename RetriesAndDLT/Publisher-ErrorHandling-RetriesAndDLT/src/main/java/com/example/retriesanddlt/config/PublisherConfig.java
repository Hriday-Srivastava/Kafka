package com.example.retriesanddlt.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PublisherConfig {

    @Bean
    public NewTopic createTopic() {
        return new NewTopic("container", 3, (short) 1);
    }

    @Bean
    public Map<String, Object> kafkaProducerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Key and Value दोनों String serializer
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public RetryTopicConfiguration retryTopicConfiguration(
            KafkaTemplate<String, String> kafkaTemplate) {

        return RetryTopicConfigurationBuilder
                .newInstance()
                .fixedBackOff(2000)
                .maxAttempts(4)
                .create(kafkaTemplate);
    }
}
