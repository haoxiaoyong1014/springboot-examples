package com.haoxy.rabbitmq.controller;

import com.haoxy.rabbitmq.model.InfoCode;
import com.haoxy.rabbitmq.model.ResInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import javax.annotation.Resource;
import java.util.UUID;

/**
 * Created by hxy on 2018/7/2.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RestController
@RequestMapping("/rabbitmq")
public class SendController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试Direct模式.
     *
     * @param p the p
     * @return the response entity
     */
    @RequestMapping("/direct")
    public String direct(String p) {
        ResInfo resInfo = new ResInfo();
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", p, correlationData);
        resInfo.setCode(InfoCode.SUCCESS);
        resInfo.setMessage("success");
        return JSON.toJSONString(resInfo);
    }

    /**
     * 测试广播模式.
     *
     * @param p the p
     * @return the response entity
     */
    @RequestMapping("/fanout")
    public ResponseEntity send(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", p, correlationData);
        return (ResponseEntity) ResponseEntity.ok();
    }
}
