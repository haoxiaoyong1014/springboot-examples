package cn.haoxy.interceptor.controller;

import cn.haoxy.interceptor.annotation.LoginRequired;
import cn.haoxy.interceptor.model.User;
import cn.haoxy.interceptor.utils.CacheCollection;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Haoxy on 2019-05-29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RestController
public class UserController {

    @LoginRequired
    @GetMapping(value = "find/{id}")
    public String findByUserId(@PathVariable String id) {
        User user = CacheCollection.getUser(id);
        return JSON.toJSONString(user);
    }
}
