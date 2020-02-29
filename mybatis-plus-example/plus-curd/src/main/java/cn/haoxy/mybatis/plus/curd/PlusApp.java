package cn.haoxy.mybatis.plus.curd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.haoxy.mybatis.plus.curd.mapper")
public class PlusApp {
    public static void main(String[] args) {
        SpringApplication.run(PlusApp.class, args);
    }
}
