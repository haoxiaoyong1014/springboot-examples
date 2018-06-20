package cn.merryyou.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hxy on 2018/6/20
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@SpringBootApplication
@RestController
public class SpringBoot2Oauth2ResourceApplication extends ResourceServerConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2Oauth2ResourceApplication.class, args);
    }

    @RequestMapping(value = "/api")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String success() {
        return "SUCCESS";
    }
}
