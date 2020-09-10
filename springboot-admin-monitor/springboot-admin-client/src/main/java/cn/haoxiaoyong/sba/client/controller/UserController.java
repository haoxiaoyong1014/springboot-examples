package cn.haoxiaoyong.sba.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haoxiaoyong
 * @date created at 下午6:48 on 2020/9/8
 * @github https://github.com/haoxiaoyong1014
 * @blog www.haoxiaoyong.cn
 */
@RestController
@Slf4j
public class UserController {

    @RequestMapping("sba")
    public String testSba(String params) {

        log.info("接口入参 {}", params);

        params = "Hello World";

        log.error("error message {}", params);

        return params;

    }
}
