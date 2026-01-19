package com.example.publisher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object>  kafkaTemplate;

    public void sendMessageToTopic(String message){
        /*CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("heart", message);
        future.whenComplete((result,ex) -> {

            if (ex == null){
                System.out.println("Sent message=[" +message + "] with offset=[" +result.getRecordMetadata().offset() + "]" );
            }
            else
            {
                System.out.println("Unable to send message=["+ message +"] due to : "+ex.getMessage());
            }

        });*/

        kafkaTemplate.send("Container", 0, null, "Hi "+message);
        kafkaTemplate.send("Container", 1, null, "Hello "+message);
        kafkaTemplate.send("Container", 2, null, "Good Morning "+message+" !");
        kafkaTemplate.send("Container", 2, null, "Good Afternoon "+message+" !");
        kafkaTemplate.send("Container", 2, null, "Good Evening "+message+" !");
        kafkaTemplate.send("Container", 3, null, "Good Night "+message+" !");

        System.out.println("Messages are published successfully !");


    }

}
