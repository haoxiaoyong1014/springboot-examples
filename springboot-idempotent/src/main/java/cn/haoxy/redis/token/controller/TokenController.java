package cn.haoxy.redis.token.controller;

import cn.haoxy.redis.token.common.ServerResponse;
import cn.haoxy.redis.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Haoxy
 * Created in 2019-08-16.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * 获取 token 的控制器
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("token")
    public ServerResponse token(){
        return tokenService.createToken();
    }

}
