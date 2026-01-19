package com.example.serializerpublisher.service;

import com.example.serializerpublisher.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCustomer(Customer customer){

        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("container","heart", customer);

            future.whenComplete((result, ex) ->{

                    System.out.println("Sent message=[" +customer + "] with offset=[" +result.getRecordMetadata().offset() + "]" );


            });

        } catch (Exception e) {

                System.out.println("Unable to send message=["+ customer +"] due to : "+e.getMessage());

        }

    }

}
