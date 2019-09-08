package cn.haoxy.redis.token.exception;

import cn.haoxy.redis.token.common.ResponseCode;
import cn.haoxy.redis.token.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Haoxy
 * Created in 2019-08-16.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ServerResponse serviceExceptionHandler(ServiceException se) {
        return ServerResponse.error(se.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ServerResponse exceptionHandler(Exception e) {
        log.error("Exception: ", e);
        return ServerResponse.error(ResponseCode.SERVER_ERROR.getMsg());
    }
}
