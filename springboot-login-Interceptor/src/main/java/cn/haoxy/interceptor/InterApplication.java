package cn.haoxy.interceptor;

import com.liumapp.redis.operator.RedisOperatorMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by Haoxy on 2019-05-29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@SpringBootApplication
@Import({
        RedisOperatorMain.class,
})
public class InterApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterApplication.class);
    }

}
