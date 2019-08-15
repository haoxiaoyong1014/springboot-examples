package cn.haoxy.redis.token.service;

import cn.haoxy.redis.token.common.ServerResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Haoxy
 * Created in 2019-08-15.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface TokenService {

    ServerResponse createToken();

    void checkToken(HttpServletRequest request);
}
