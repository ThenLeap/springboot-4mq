package com.jony.demo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic myTopic() {
        return new NewTopic("my-topic", 1, (short) 1);
    }
}
