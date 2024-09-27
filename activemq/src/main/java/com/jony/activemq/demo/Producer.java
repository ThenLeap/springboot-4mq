package com.jony.activemq.demo;

import jakarta.annotation.Resource;
import jakarta.jms.Destination;
import jakarta.jms.Queue;
import jakarta.jms.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

    @Resource(name = "springboot.queue")
    private Queue queue;

    @Resource(name = "springboot.topic")
    private Topic topic;


    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /**
     * 发送消息，destination是发送到的目标队列，message是待发送的消息内容;
     */
    public void sendMessage(Destination destination, final String message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送队列消息
     */
    public void sendQueueMessage(final String message) {
        sendMessage(queue, message);
    }

    /**
     * 发送Topic消息
     */
    public void sendTopicMessage(final String message) {
        sendMessage(topic, message);
    }

}
