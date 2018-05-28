package com.hxy.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by hxy on 2018/5/24.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component

public class TopicReceiver1 {

    @RabbitHandler
    @RabbitListener(queues = "topic.message")
    public void process(String message) {
        System.out.println("Topic Receiver1  : " + message);
    }
}
