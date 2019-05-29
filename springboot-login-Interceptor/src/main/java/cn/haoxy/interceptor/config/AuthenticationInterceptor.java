package cn.haoxy.interceptor.config;

import cn.haoxy.interceptor.annotation.LoginRequired;
import cn.haoxy.interceptor.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by Haoxy on 2019-05-29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //如果不是映射到方法就不需要拦截
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //得到请求的哪个方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断是否这个接口是否需要登录才能访问
        LoginRequired annotation = method.getAnnotation(LoginRequired.class);
        if (annotation != null) {
            //这个方法上含有这个注解,说明需要登录才能请求
            String token = httpServletRequest.getHeader("token");
            if (token == null) {
                throw new RuntimeException("无token，请重新登录");
            }
            Claims claims = TokenUtils.parseJWT(token);
            String UserId = claims.getId();
        } else {
            return true;
        }


        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
