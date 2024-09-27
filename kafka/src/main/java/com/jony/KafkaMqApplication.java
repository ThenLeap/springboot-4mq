package com.jony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaMqApplication {

    // http://localhost:8083/doc.html
    public static void main(String[] args) {
        SpringApplication.run(KafkaMqApplication.class, args);
    }

}
