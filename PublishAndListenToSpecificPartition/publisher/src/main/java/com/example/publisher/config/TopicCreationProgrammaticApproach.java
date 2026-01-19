package com.example.publisher.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicCreationProgrammaticApproach {

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("Container", 4, (short) 1);
    }
}
