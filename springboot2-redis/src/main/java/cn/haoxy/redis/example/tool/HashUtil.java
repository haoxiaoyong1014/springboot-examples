package cn.haoxy.redis.example.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import sun.java2d.cmm.kcms.KcmsServiceProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by haoxy on 2018/11/30.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component
public class HashUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void put(String key, String hashKey, String hashValue) {

        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    public void putAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public void delete(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    public Map<String, Object> entries(String key) {

        return redisTemplate.opsForHash().entries(key);
    }

    public Object get(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public List<Object> multiGet(String key, Collection<Object> hashKey) {
        return redisTemplate.opsForHash().multiGet(key, hashKey);
    }

    public Set<Object> keys(String key) {
        return redisTemplate.opsForHash().keys(key);

    }

    public long size(String key){
        return redisTemplate.opsForHash().size(key);
    }
    public List<Object> hashValues(String key){
        return redisTemplate.opsForHash().values(key);
    }
}
