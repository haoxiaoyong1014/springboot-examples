package cn.haoxy.redis.token.service.impl;

import cn.haoxy.redis.token.common.ServerResponse;
import cn.haoxy.redis.token.service.TestService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Haoxy
 * Created in 2019-08-16.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
public class TestServiceImpl implements TestService {

    private static int count=0;

    @Override
    public ServerResponse testIdempotence() {
        count++;
        SimpleDateFormat simpt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        System.out.println(simpt.format(new Date()) + "-" + ++count);
        return ServerResponse.success("testIdempotence: success");
    }

}
