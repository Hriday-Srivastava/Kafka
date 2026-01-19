package com.example.deserializerlistener.service;

import com.example.deserializerlistener.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "container", groupId = "customer-group")
    public void consume(@Header(KafkaHeaders.RECEIVED_KEY) String key, Customer customer){   //We need tell what data type of message to consume or listen.
        log.info("Consumer consume the message = {}", customer);
        log.info("Key = {}", key);
    }

}
