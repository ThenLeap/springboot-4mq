package com.jony.rabbitmq.abandon;

import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @消息队列发送工具类
 * @Author jony
 * @Date 2024/9/25
 */
@Component
public class RabbitMQSend implements ConfirmCallback, ReturnCallback {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQSend.class);
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSend(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setConfirmCallback(this::confirm);
        this.rabbitTemplate.setReturnsCallback(this::returnedMessage);
    }

    /**
     * 发送消息
     *
     * @param exchange   交换机名称
     * @param routingKey 路由键
     * @param json       消息内容
     */
    public void sendMessage(String exchange, String routingKey, String json) {
        Message message = createMessage(json);
        this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    /**
     * 创建消息
     *
     * @param json 消息内容
     * @return 消息对象
     */
    private Message createMessage(String json) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        return new Message(json.getBytes(), messageProperties);
    }

    /**
     * 消息确认回调
     */
    private void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功，CorrelationData: {}", correlationData);
        } else {
            log.warn("消息发送失败: {}, 原因: {}", correlationData, cause);
        }
    }

    /**
     * 消息返回回调
     */
    private void returnedMessage(ReturnedMessage returnedMessage) {
        Message message = returnedMessage.getMessage();
        int replyCode = returnedMessage.getReplyCode();
        String replyText = returnedMessage.getReplyText();
        String exchange = returnedMessage.getExchange();
        String routingKey = returnedMessage.getRoutingKey();
        log.error("消息发送失败，内容: {}, 回复码: {}, 回复信息: {}, 交换机: {}, 路由键: {}", new String(message.getBody()), replyCode, replyText, exchange, routingKey);

    }

    /**
     * 发布/订阅模式发送
     */
    public void routeSend(String json) {
        sendMessage(RabbitMQConstant.FANOUT_EXCHANGE, "", json);
    }

    /**
     * 简单模式发送
     */
    public void simpleSend(String json) {
        sendMessage(RabbitMQConstant.QUEUE_1, "", json);
    }

    /**
     * 路由模式发送
     */
    public void routingSend(String routingKey, String json) {
        sendMessage(RabbitMQConstant.DIRECT_EXCHANGE, routingKey, json);
    }

    /**
     * 主题模式发送
     */
    public void topicSend(String routingKey, String json) {
        sendMessage(RabbitMQConstant.TOPIC_EXCHANGE, routingKey, json);
    }

    /**
     * 死信模式发送
     */
    public void beadSend(String routingKey, Message message) {
        this.rabbitTemplate.convertAndSend(RabbitMQConstant.DEAD_EXCHANGE, routingKey, message);
    }

    @Override
    public void handle(long l, boolean b) throws IOException {

    }

    @Override
    public void handle(Return aReturn) {

    }
}
