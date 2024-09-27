package com.jony.activemq.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MsgController {

    @Autowired
    private Producer producer;

    @Autowired
    private Consumer consumer;

    @GetMapping("/sendQueue")
    public String sendQueueMsg() {
        User user = new User();
        user.setId(1L);
        user.setUsername("一一哥Queue");
        user.setPassword("123");
        producer.sendQueueMessage(user.toString());
        return "发送成功!";
    }

    @GetMapping("/sendTopic")
    public String sendTopicMsg() {
        User user = new User();
        user.setId(2L);
        user.setUsername("一一哥Topic");
        user.setPassword("123456");
        producer.sendTopicMessage(user.toString());
        return "发送成功!";
    }

}
