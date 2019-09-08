package cn.haoxy.redis.token.controller;

import cn.haoxy.redis.token.annotation.ApiIdempotent;
import cn.haoxy.redis.token.common.ServerResponse;
import cn.haoxy.redis.token.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haoxy
 * Created in 2019-08-16.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiIdempotent
    @PostMapping("testIdempotence")
    public ServerResponse testIdempotence(){
        return testService.testIdempotence();
    }
}
