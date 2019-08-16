package cn.haoxy.redis.token.service;

import cn.haoxy.redis.token.common.ServerResponse;

/**
 * @author Haoxy
 * Created in 2019-08-16.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface TestService {

    ServerResponse testIdempotence();
}
