package com.example.listener.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;



@Service
public class KafkaMessageListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(groupId = "mrheart",
            topicPartitions = {
                    @TopicPartition(topic = "Container", partitions = {"2"})
            })
    public void consume(String message){   //We need tell what data type of message to consume or listen.
    log.info("Consumer consume the message = {}", message);
    }


}
