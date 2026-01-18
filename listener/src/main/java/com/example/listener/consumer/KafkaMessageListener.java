package com.example.listener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;



@Service
public class KafkaMessageListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "heart", groupId = "mrheart")
    public void consume(String message){   //We need tell what data type of message to consume or listen.
    log.info("Consumer consume the message = {}", message);
    }


}
