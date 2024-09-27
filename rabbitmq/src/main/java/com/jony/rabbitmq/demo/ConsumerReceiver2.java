package com.jony.rabbitmq.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * 自动ACK模式的消费者
 *
 * @author jony
 */
// @Component
public class ConsumerReceiver2 {


    // 直连模式的多个消费者，会分到其中一个消费者进行消费。类似task模式
    // 通过注入RabbitContainerFactory对象，来设置一些属性，相当于task里的channel.basicQos
    @RabbitListener(queues = "helloWorldQueue")
    public void helloWorldReceive(String message) {

        System.out.println("helloWorld模式 received message : " + message);
    }

    // 工作队列模式
    @RabbitListener(queues = "work_sb_mq_q")
    public void wordQueueReceive1(String message) {

        System.out.println("工作队列模式1 received message : " + message);
    }

    @RabbitListener(queues = "work_sb_mq_q")
    public void wordQueueReceive2(String message) {

        System.out.println("工作队列模式2 received message : " + message);
    }


    // pub/sub模式进行消息监听
    @RabbitListener(queues = "fanout.q1")
    public void fanoutReceive1(String message) {

        System.out.println("发布订阅模式1received message : " + message);
    }

    @RabbitListener(queues = "fanout.q2")
    public void fanoutReceive2(String message) {

        System.out.println("发布订阅模式2 received message : " + message);
    }


    // Routing路由模式
    @RabbitListener(queues = "direct_sb_mq_q1")
    public void routingReceive1(String message) {

        System.out.println("Routing路由模式routingReceive11111 received message : " + message);
    }

    @RabbitListener(queues = "direct_sb_mq_q2")
    public void routingReceive2(String message) {

        System.out.println("Routing路由模式routingReceive22222 received message : " + message);
    }


    // topic 模式
    // 注意这个模式会有优先匹配原则。例如发送routingKey=hunan.IT,那匹配到hunan.*(hunan.IT,hunan.eco),之后就不会再去匹配*.ITd
    @RabbitListener(queues = "topic_sb_mq_q1")
    public void topicReceive1(String message) {
        System.out.println("Topic模式 topic_sb_mq_q1 received message : " + message);
    }

    @RabbitListener(queues = "topic_sb_mq_q2")
    public void topicReceive2(String message) {
        System.out.println("Topic模式 topic_sb_mq_q2 received  message : " + message);
    }

}
