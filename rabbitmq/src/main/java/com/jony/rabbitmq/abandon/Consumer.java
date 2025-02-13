package com.jony.rabbitmq.abandon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @消息队列消费者
 * @Author jony
 * @Date 2024/9/25
 */

@Configuration
public class Consumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = RabbitMQConstant.QUEUE_1)
    public void simplConsumer1(Message message, com.rabbitmq.client.Channel channel) {
        try {
            byte[] body = message.getBody();
            String json = new String(body);
            log.info("queue_1收到消息 : " + json);
            // 手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = RabbitMQConstant.QUEUE_2)
    public void simplConsumer2(Message message, com.rabbitmq.client.Channel channel) {
        try {
            byte[] body = message.getBody();
            String json = new String(body);
            log.info("queue_2收到消息 : " + json);
            // 手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当死信队列的消息过期后,会通过死信交换机把过期消息发送到这里
     *
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMQConstant.CONSUMER_BEAD_QUEUE)
    public void simplConsumer3(Message message, com.rabbitmq.client.Channel channel) {
        try {
            byte[] body = message.getBody();
            String json = new String(body);
            log.info("consumer_bead_queue收到消息 : " + json);
            // 手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
