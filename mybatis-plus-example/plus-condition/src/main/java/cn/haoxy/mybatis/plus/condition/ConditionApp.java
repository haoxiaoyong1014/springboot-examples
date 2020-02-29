package cn.haoxy.mybatis.plus.condition;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "cn.haoxy.mybatis.plus.condition")
public class ConditionApp {
    public static void main(String[] args) {
        SpringApplication.run(ConditionApp.class, args);
    }
}
