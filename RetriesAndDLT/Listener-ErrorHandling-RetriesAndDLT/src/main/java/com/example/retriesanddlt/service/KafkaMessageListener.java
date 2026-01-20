package com.example.retriesanddlt.service;


import com.example.retriesanddlt.entities.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaMessageListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RetryableTopic(
            attempts = "4"

    )
    @KafkaListener(topics = "container", groupId = "customer-group")
    public void consume(@Header(KafkaHeaders.RECEIVED_TOPIC) String key, String customerJson) {   //We need tell what data type of message to consume or listen.

        try {
            Customer customer = objectMapper.readValue(customerJson, Customer.class);

            log.info("Received Customer Object : {}", customer);

            // Example validation
            if (customer.getAge() < 18) {
                throw new RuntimeException(
                        "Minor Customer named : " + customer.getCustomerName() + " is not allowed..."
                );
            }

        } catch (Exception e) {
            log.error("Error while converting JSON to Customer Object : " + e.getMessage());
            throw new RuntimeException(e);
        }

    }


    @DltHandler
    public void listenDLT(String customer, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("DLT received : {}, from : {}", customer, topic);
    }

}
