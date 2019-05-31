package cn.haoxy.interceptor.config;

import cn.haoxy.interceptor.annotation.LoginRequired;
import cn.haoxy.interceptor.model.User;
import cn.haoxy.interceptor.service.UserService;
import cn.haoxy.interceptor.utils.TokenUtils;
import cn.haoxy.redis.example.tool.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created by Haoxy on 2019-05-29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private StringUtil stringUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法就不需要拦截
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //得到请求的哪个方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断是否这个方法是否需要登录才能访问
        LoginRequired annotation = method.getAnnotation(LoginRequired.class);
        if (annotation != null) {
            //这个方法上含有这个注解,说明需要登录才能请求
            //获取请求头上的token
            String token = request.getHeader("token");
            if (token == null) {
                throw new RuntimeException("无token，请重新登录");
            }
            Claims claims = null;
            try {
                claims = TokenUtils.parseJWT(token);
            } catch (ExpiredJwtException e) {
                //抛出此异常说明 token 已经过期
                /*
                 * 这个刷新token的问题,我想在这里我想记录一下我的想法,在他第一次登陆的时候,我们生成两个token,分别为atoken和rtoken,
                 * 其中rtoken 不能做业务的操作,rtoken 的作用就是当 atoken 过期了之后,用 rtoken 来换取新的 atoekn,这个前提是
                 * 一般我们 atoken 的有效期为 2 个小时,rtoken 的过期时间为一周或者 15 天;如果rtoken都过期了那就要从新登陆了;
                 * 具体做法有两种: 1,我们生成rtoken 存在redis中,key为 atoken,value为rtoken;当检测要atoken过期了,我们从 redis中取出
                 * rtoken;判断是否存在或者是否过期;如果存在并没有过期,我们就生成一个新的atoken;response给前端,前端拿到新的atoken,从新请求;
                 * 并做到用户无感;
                 * 在高并发情况在这个是有缺陷的;
                 * 2,token的过期是否过期前端来判断,登录的时候将atoken和rtoken都返回给前端,
                 */
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=UTF-8");
                ServletOutputStream out = response.getOutputStream();
                JSONObject object = new JSONObject();
                //如果 token 过期了以后,这个过期的 token 就会放入黑名单中;获取的 claims就是 null值;所以这个要用到 redis或者mysql 来拿 userId;或者用rtoken来换atoken
                Object rtoken = stringUtil.get(token);
                if (rtoken == null) {
                    throw new RuntimeException("token失效，请重新登录");
                }
                Claims rclaims = TokenUtils.parseJWT(rtoken.toString());
                String newToken = TokenUtils.createJwtToken(rclaims.getId());
                stringUtil.del(token);
                stringUtil.set(newToken, rtoken.toString(), 7, TimeUnit.DAYS);
                object.put("newToken", newToken);
                object.put("status", 1);
                object.put("message", "token expiration");
                out.print(JSON.toJSONString(object));
                out.flush();
                out.close();
                return false;
            }catch (SignatureException e){
                throw new RuntimeException("无效token....");
            }
            //rtoken 没有操作业务的能力,rtoken的目的就是从中拿到用户id
            if ("rtoken@admin".equals(claims.getSubject())) {
                throw new RuntimeException("无效token....");
            }

            User user = userService.findById(claims.getId());
            if (user == null) {
                throw new RuntimeException("用户不存在，请重新登录");
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
