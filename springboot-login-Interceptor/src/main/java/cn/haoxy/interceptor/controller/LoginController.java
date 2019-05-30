package cn.haoxy.interceptor.controller;

import cn.haoxy.interceptor.model.User;
import cn.haoxy.interceptor.service.UserService;
import cn.haoxy.interceptor.utils.TokenUtils;
import cn.haoxy.redis.example.tool.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Created by Haoxy on 2019-05-29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringUtil stringUtil;


    @PostMapping("login")
    public Object login(@RequestBody User user) {
        User userInDataBase = userService.findByName(user.getName(), user.getPassword());
        JSONObject jsonObject = new JSONObject();
        if (userInDataBase == null) {
            jsonObject.put("error", "用户不存在");
        } else {
            String atoken = TokenUtils.createJwtToken(userInDataBase.getId());
            String rtoken = TokenUtils.createJwtrToken(userInDataBase.getId());
            stringUtil.set(atoken, rtoken, 7, TimeUnit.DAYS); //redis
            jsonObject.put("token", atoken);
            jsonObject.put("user", userInDataBase);
        }
        return jsonObject;
    }
}
