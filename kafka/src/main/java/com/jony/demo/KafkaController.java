package com.jony.demo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/send")
    @Operation(summary = "Send message to Kafka topic")
    public String sendMessage(@RequestParam String message) {
        kafkaProducer.sendMessage("my-topic", message);
        return "Message sent";
    }
}
