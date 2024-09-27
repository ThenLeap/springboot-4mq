package com.jony.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 手动ACK模式的消费者
 *
 * @author jony
 */
@Component
public class ConsumerReceiver {


    // 直连模式的多个消费者，会分到其中一个消费者进行消费。类似task模式
    // 通过注入RabbitContainerFactory对象，来设置一些属性，相当于task里的channel.basicQos
    @RabbitListener(queues = "helloWorldQueue", ackMode = "MANUAL")
    public void helloWorldReceive(Channel channel, Message message) throws IOException {
        System.out.println("helloWorld模式 received message : " + new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    // 工作队列模式
    @RabbitListener(queues = "work_sb_mq_q", ackMode = "MANUAL")
    public void wordQueueReceive1(Message message, Channel channel) throws IOException {
        String messageBody = new String(message.getBody());
        System.out.println("工作队列模式1 received message : " + messageBody);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(queues = "work_sb_mq_q", ackMode = "MANUAL")
    public void wordQueueReceive2(Message message, Channel channel) throws IOException {
        String messageBody = new String(message.getBody());
        System.out.println("工作队列模式2 received message : " + messageBody);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    // pub/sub模式进行消息监听
    @RabbitListener(queues = "fanout.q1", ackMode = "MANUAL")
    public void fanoutReceive1(Message message, Channel channel) throws IOException {
        String messageBody = new String(message.getBody());
        System.out.println("发布订阅模式1 received message : " + messageBody);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(queues = "fanout.q2", ackMode = "MANUAL")
    public void fanoutReceive2(Message message, Channel channel) throws IOException {
        String messageBody = new String(message.getBody());
        System.out.println("发布订阅模式2 received message : " + messageBody);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    // direct路由模式
    @RabbitListener(queues = "direct_sb_mq_q1", ackMode = "MANUAL")
    public void routingReceive1(Message message, Channel channel) throws IOException {
        String messageBody = new String(message.getBody());
        System.out.println("Direct模式directReceive11111 received message : " + messageBody);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(queues = "direct_sb_mq_q2", ackMode = "MANUAL")
    @RabbitListener(queues = "direct_sb_mq_q2", ackMode = "MANUAL")
    public void routingReceive2(Message message, Channel channel) throws IOException {
        String messageBody = new String(message.getBody());
        System.out.println("Direct模式directReceive22222 received message : " + messageBody);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    // topic 模式
    // 注意这个模式会有优先匹配原则。例如发送routingKey=hunan.IT,那匹配到hunan.*(hunan.IT,hunan.eco),之后就不会再去匹配*.ITd
    @RabbitListener(queues = "topic_sb_mq_q1", ackMode = "MANUAL")
    public void topicReceive1(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        System.out.println("Topic模式 topic_sb_mq_q1 received message : " + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


    @RabbitListener(queues = "topic_sb_mq_q2", ackMode = "MANUAL")
    public void topicReceive2(Message message, Channel channel) throws IOException {
        String body = new String(message.getBody());
        System.out.println("Topic模式 topic_sb_mq_q2 received message : " + body);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
