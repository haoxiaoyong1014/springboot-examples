package cn.haoxy.redis.example.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by haoxy on 2018/11/29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component
public class StringUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置一个字符串
     *
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置一个引用数据类型
     *
     * @param key
     * @param value
     */
    public void setObj(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 指定时间内有效的 value
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    public void set(String key, String value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 如果 key 值已经存在,返回 false,不会进行插入
     * 不存在返回 true, 并插入
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 为多个键分别设置它们的值
     *
     * @param map
     */
    public void multiSet(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 批量获取值
     *
     * @param list
     * @return
     */
    public List<Object> multiGet(List<String> list) {
        return redisTemplate.opsForValue().multiGet(list);
    }

    /**
     * 为多个键分别设置它们的值,如果存在返回 false,不会进行插入,
     * 不存在返回 true,并插入
     *
     * @param map
     */

    public boolean multiSetIfAbsent(Map<String, Object> map) {
        return redisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 设置键的字符串值并返回其旧值
     *
     * @param key
     * @param value
     * @return
     */
    public Object getAndSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    /**
     * 支持整数,并返回其插入的整数值
     *
     * @param key
     * @param value
     * @return
     */
    public long increment(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     * 支持浮点类型,并返回其插入的值
     *
     * @param key
     * @param value
     * @return
     */
    public double increment(String key, double value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    /**
     * 返回一个key对应的value的长度
     *
     * @param key
     * @return
     */
    public long size(String key) {
        return redisTemplate.opsForValue().size(key);
    }

    /**
     * 根据 key获取 value
     *
     * @param key
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据 key删除 value
     *
     * @param key
     */
    public Object del(String key) {
        return redisTemplate.delete(key);
    }



}
