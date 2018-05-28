package com.hxy.rabbitmq.many;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by hxy on 2018/5/24.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component

public class NeoReceiver1 {

    @RabbitHandler
    @RabbitListener(queues = "neo")
    public void process(String neo) {
        System.out.println("Receiver 1: " + neo);
    }
}
