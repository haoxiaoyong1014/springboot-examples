package com.hxy.rabbitmq.object;

import com.hxy.rabbitmq.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by hxy on 2018/5/24.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component

public class ObjectReceiver {

    @RabbitHandler
    @RabbitListener(queues = "object")
    public void process(User user) {
        System.out.println("Receiver object : " + user);
    }
}
