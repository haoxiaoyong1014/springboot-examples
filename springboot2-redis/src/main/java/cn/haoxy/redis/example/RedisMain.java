package cn.haoxy.redis.example;

import cn.haoxy.redis.example.config.RedisConfig;
import cn.haoxy.redis.example.tool.StringUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by haoxy on 2018/11/29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Configuration
@EnableAutoConfiguration
@Import({RedisConfig.class})
public class RedisMain {

    @Bean
    public StringUtil stringUtil(){
        return new StringUtil();
    }

}
