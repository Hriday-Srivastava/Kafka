package com.example.retriesanddlt.config;

import com.example.retriesanddlt.entities.Customer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class ListenerConfig {

    @Bean
    public Map<String, Object> kafkaConsumerConfig() {


        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // Trust package
        props.put("spring.json.trusted.packages",
                "com.example.retriesanddlt.entities");

        //  Type mapping (important). “We are telling When you see
        //com.example.serializerpublisher.entities.Customer
        //convert it into
        //com.example.deserializerlistener.entities.Customer”
        //NOTE: In real time we user shard library for DTO object in both Publisher and Listener
        props.put("spring.json.type.mapping",
                "com.example.retriesanddlt.entities.Customer:" +
                        "com.example.retriesanddlt.entities"
        );

        return props;


    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


}
