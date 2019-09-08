package cn.haoxy.interceptor.service;

import cn.haoxy.interceptor.model.User;
import cn.haoxy.interceptor.utils.CacheCollection;
import org.springframework.stereotype.Service;

/**
 * Created by Haoxy on 2019-05-29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
public class UserService {

    public User findById(String id) {
        User user = CacheCollection.getUser(id);
        if (user != null) {
            return user;
        }
        return null;
    }
    public User findByName(String name,String password) {
        User user = CacheCollection.getUserByName(name,password);
        if (user != null) {
            return user;
        }
        return null;
    }

}
