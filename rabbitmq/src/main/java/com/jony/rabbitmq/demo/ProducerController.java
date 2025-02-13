package com.jony.rabbitmq.demo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // helloWorld 直连模式
    @Operation(summary = "helloWorld发送接口", description = "直接发送到队列")
    @GetMapping(value = "/helloWorldSend")
    public Object helloWorldSend(String message) throws AmqpException, UnsupportedEncodingException {
        // 设置部分请求参数
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);

        // 发消息
        rabbitTemplate.send("helloWorldQueue", new Message(message.getBytes("UTF-8"), messageProperties));
        return "message had sent : " + message;
    }


    // 工作队列模式
    @Operation(summary = "workQueue发送接口", description = "发送到所有监听该队列的消费")
    @GetMapping(value = "/workQueueSend")
    public Object workQueueSend(String message) throws AmqpException, UnsupportedEncodingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // 制造多个消息进行发送操作
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.send("work_sb_mq_q", new Message(message.getBytes("UTF-8"), messageProperties));
        }
        return "message had sent : " + message;
    }


    // pub/sub 发布订阅模式   交换机类型 fanout
    @Operation(summary = "fanout发送接口", description = "发送到fanoutExchange。消息将往该exchange下的所有queue转发")
    @GetMapping(value = "/fanoutSend")
    public Object fanoutSend(String message) throws AmqpException, UnsupportedEncodingException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("fanoutExchange", "", new Message(message.getBytes("UTF-8"), messageProperties));
        return "message had sent : " + message;
    }


    // direct 工作模式  交换机类型 direct
    @Operation(summary = "direct发送接口", description = "发送到directExchange。exchange转发消息时，会往routingKey匹配的queue发送")
    @GetMapping(value = "/directSend")
    public Object routingSend(String routingKey, String message) throws AmqpException, UnsupportedEncodingException {

        if (null == routingKey) {
            routingKey = "china.changsha";
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("directExchange", routingKey, new Message(message.getBytes("UTF-8"), messageProperties));
        return "message had sent : routingKey >" + routingKey + ";message > " + message;
    }


    // topic 工作模式   交换机类型 topic
    @Operation(summary = "topic发送接口", description = "发送到topicExchange。exchange转发消息时，会往routingKey匹配的queue发送，*代表一个单词，#代表0个或多个单词。")
    @GetMapping(value = "/topicSend")
    public Object topicSend(String routingKey, String message) throws AmqpException, UnsupportedEncodingException {

        if (null == routingKey) {
            routingKey = "changsha.kf";
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        // fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send("topicExchange", routingKey, new Message(message.getBytes("UTF-8"), messageProperties));
        return "message had sent : routingKey >" + routingKey + ";message > " + message;
    }

}
