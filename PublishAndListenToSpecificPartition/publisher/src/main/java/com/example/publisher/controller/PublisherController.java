package com.example.publisher.controller;

import com.example.publisher.service.KafkaMessagePublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publisher")
public class PublisherController {

    private final KafkaMessagePublisher kafkaMessagePublisher;

    public PublisherController(KafkaMessagePublisher kafkaMessagePublisher) {
        this.kafkaMessagePublisher = kafkaMessagePublisher;
    }

    @GetMapping("/{publish}")
    public ResponseEntity<String> publishMessage(@PathVariable String publish) {
        try {

                kafkaMessagePublisher.sendMessageToTopic(publish);
            return ResponseEntity.ok("Message published successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while publishing");
        }
    }
}
