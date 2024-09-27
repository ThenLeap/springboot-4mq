package com.jony.rocketmq.demo;

import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rocketmq")
public class RocketMQController {

    @Autowired
    private MQProducerService mqProducerService;

    @GetMapping("/send")
    public void send() {
        User user = User.builder().id(12).username("jony").build();
        mqProducerService.send(user);
    }

    @GetMapping("/sendTag")
    public ResponseEntity<SendResult> sendTag() {
        SendResult sendResult = mqProducerService.sendTagMsg("带有tag的字符消息");
        return ResponseEntity.ok(sendResult);
    }

}
