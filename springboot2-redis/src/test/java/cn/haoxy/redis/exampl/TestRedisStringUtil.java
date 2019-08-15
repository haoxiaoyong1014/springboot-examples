package cn.haoxy.redis.exampl;

import cn.haoxy.redis.example.RedisMain;
import cn.haoxy.redis.example.common.User;
import cn.haoxy.redis.example.tool.StringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by haoxy on 2018/11/29.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMain.class)
public class TestRedisStringUtil {

    @Autowired
    private StringUtil stringUtil;

    /**
     * 测试设置一个字符串
     */
    @Test
    public void setTest() {
        stringUtil.set("name_1_2", "小永");
    }

    /**
     * 测试添加一个引用数据类型
     */
    @Test
    public void setObj() {
        stringUtil.setObj("user", new User("小永", 23));
    }

    /**
     * 测试设置一个字符串_指定时间内有效
     */
    @Test
    public void setTest_timeout() {
        stringUtil.set("name_2", "tom_2", 20, TimeUnit.SECONDS); //20秒之后将返回 null
    }

    /**
     * key 值存在返回 false,不进行插入, 否则返回true,并插入
     */
    @Test
    public void setIfAbsent() {
        boolean tag = stringUtil.setIfAbsent("name_1_1", "boy"); //返回 false ,因为name_1_1已经存在,否则返回 true,并存储
        System.out.println(tag);
    }

    /**
     * 批量插入值
     */
    @Test
    public void multiSet() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("multi1", "multi1");
        maps.put("multi2", "multi2");
        maps.put("multi3", 3);
        stringUtil.multiSet(maps);
    }

    /**
     * 批量获取值
     */
    @Test
    public void multiGet() {
        List<String> list = new ArrayList<>();
        list.add("multi1");
        list.add("multi2");
        list.add("multi3");
        List<Object> list1 = stringUtil.multiGet(list);
        System.out.println(list1);   //[multi1, multi2, 3]
    }

    /**
     * 批量插入, key 值存在返回 false,不进行插入, 否则返回true,并插入
     * 有一个 key 已经存在就会返回 false,不同的那个key也不会插入
     */
    @Test
    public void multiSetIfAbsent() {
        Map<String, Object> maps = new HashMap<>();
        maps.put("multi1", "multi1");
        maps.put("multi2", "multi2");
        maps.put("multi4", 4);
        boolean tag = stringUtil.multiSetIfAbsent(maps);
        System.out.println(tag);  // false
    }

    /**
     * 设置键的字符串值并返回其旧值
     */
    @Test
    public void getAndSet() {
        Object andSet = stringUtil.getAndSet("name_1_1", "boy");
        System.out.println(andSet);
    }

    /**
     * 支持整数,并返回其插入的整数值
     */
    @Test
    public void increment() {
        long int_value = stringUtil.increment("int_value", 2);
        System.out.println(int_value);//2
    }

    /**
     * 支持浮点,并返回其插入的值
     */
    @Test
    public void incrementDouble() {
        double int_value = stringUtil.increment("double_value", 2.1);
        System.out.println(int_value);//2.1
    }


    @Test
    public void getTest() {
        Object name_1 = stringUtil.get("name_1_2");
        System.out.println(name_1);
    }
}
