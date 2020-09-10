package cn.haoxiaoyong.sba.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author haoxiaoyong
 * @date created at 下午5:18 on 2020/9/7
 * @github https://github.com/haoxiaoyong1014
 * @blog www.haoxiaoyong.cn
 */
@SpringBootApplication
@EnableAdminServer
public class AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
