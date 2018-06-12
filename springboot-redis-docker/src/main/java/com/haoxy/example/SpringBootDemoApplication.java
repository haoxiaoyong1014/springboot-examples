package com.haoxy.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * Created by hxy on 2018/6/12.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RestController
@RequestMapping("/")
@SpringBootApplication
public class SpringBootDemoApplication {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/count")
    public Integer getCount() {
        //从缓存中取得字符串数据
        String count = redisTemplate.opsForValue().get("count");
        Integer total = Integer.parseInt(count == null ? "0" : count);
        //将数据存入缓存
        redisTemplate.opsForValue().set("count", String.valueOf(total+1));
        return total;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
