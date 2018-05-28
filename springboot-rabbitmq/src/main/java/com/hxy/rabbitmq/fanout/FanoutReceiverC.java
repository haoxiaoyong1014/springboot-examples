package com.hxy.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by hxy on 2018/5/24.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component

public class FanoutReceiverC {
    @RabbitHandler
    @RabbitListener(queues = "fanout.C")
    public void process(String message) {
        System.out.println("fanout Receiver C: " + message);
    }
}
