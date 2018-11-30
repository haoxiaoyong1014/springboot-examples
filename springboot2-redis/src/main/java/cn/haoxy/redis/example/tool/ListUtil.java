package cn.haoxy.redis.example.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Created by haoxy on 2018/11/30.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Component
public class ListUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量把一个集合插入到列表中
     * @param key
     * @param value
     * @return
     */
    public long leftPushAll(String key, Collection<Object> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 批量把一个数组插入到列表中
     *
     * @param key
     * @param value
     * @return
     */
    public long leftPushAll(String key, Object... value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 将所有指定的值插入存储在键的列表的头部。如果键不存在，
     * 则在执行推送操作之前将其创建为空列表。（从左边插入）
     *
     * @param key
     * @param value
     */
    public void leftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将所有指定的值插入存储在键的列表的头部。
     * 如果键不存在，则在执行推送操作之前将其创建为空列表。（从右边插入）
     * @param key
     * @param value
     */
    public void rightPush(String key, Object value){
        redisTemplate.opsForList().rightPush(key,value);
    }

    /**
     * 返回存储在键中的列表的长度。如果键不存在，则将其解释为空列表，并返回0。
     * 当key存储的值不是列表时返回错误。
     *
     * @param key
     * @return
     */
    public long size(String key) {
        return redisTemplate.opsForList().size(key);
    }


    /**
     * 返回存储在键中的列表的指定元素。
     * 偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
     *
     * @param key
     * @return
     */
    public List<Object> range(String key,long start,long end) {

        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 根据下标获取其值
     * @param key
     * @param index
     * @return
     */
    public Object index(String key,long index){
        return redisTemplate.opsForList().index(key,index);
    }

    /**
     * 弹出最左边的元素，弹出之后该值在列表中将不复存在
     * @param key
     */
    public void leftPop(String key){
        redisTemplate.opsForList().leftPop(key);
    }

    public void rightPop(String key){
        redisTemplate.opsForList().rightPop(key);
    }
}
