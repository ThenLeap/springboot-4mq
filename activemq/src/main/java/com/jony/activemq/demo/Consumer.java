package com.jony.activemq.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    /**
     * 监听Queue队列，queue类型
     */
    @JmsListener(destination = "springboot.queue", containerFactory = "jmsQueueListenerContainerFactory")
    public void receiveQueue(String text) {
        log.warn(this.getClass().getName() + "-->收到的报文为:" + text);
    }

    /**
     * 监听Topic队列，topic类型，这里containerFactory要配置为jmsTopicListenerContainerFactory
     */
    @JmsListener(destination = "springboot.topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void receiveTopic(String text) {
        log.warn(this.getClass().getName() + "-->收到的报文为:" + text);
    }

}
