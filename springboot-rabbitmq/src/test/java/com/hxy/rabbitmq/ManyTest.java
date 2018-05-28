package com.hxy.rabbitmq;

import com.hxy.rabbitmq.many.NeoSender1;
import com.hxy.rabbitmq.many.NeoSender2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by hxy on 2018/5/24.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManyTest {

    @Autowired
    private NeoSender1 neoSender;

    @Autowired
    private NeoSender2 neoSender2;

    @Test
    public void oneToMany() throws Exception {
        for (int i=0;i<100;i++){
            neoSender.send(i);
        }
    }

    @Test
    public void manyToMany() throws Exception {
        for (int i=0;i<100;i++){
            neoSender.send(i);
            neoSender2.send(i);
        }
    }
}
