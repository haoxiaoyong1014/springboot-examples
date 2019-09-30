package com.haoxy.example.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.haoxy.example.model.Person;
import com.haoxy.example.page.PageInfo;
import com.haoxy.example.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hxy on 2018/6/27.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@RestController
public class PersonController {
    private Logger logger = LoggerFactory.getLogger(PersonController.class);
    @Autowired
    private PersonService personService;

    @RequestMapping("/add")
    public void insert() {
        for (int i = 0; i < 1000; i++) {
            Person person = new Person();
            person.setName("xiaoxiao" + i);
            person.setAddress("address" + i);
            person.setAge(10 + i);
            personService.insert(person);
        }
    }

    @RequestMapping("/update")
    public String update(@RequestBody Person person) {
        Person person1 = personService.findById(person.getId());
        person1.setName("dddddd");
        person1.setAge(11);
        int a = personService.uptatePerson(person1);
        if (a > 0) {
            return JSON.toJSONString("SUCCESS");
        }
        return JSON.toJSONString("ERROR");
    }

    @RequestMapping("/findAll")
    public String findAll() {
        long begin = System.currentTimeMillis();
        List<Person> persons = personService.findAll();
        long ing = System.currentTimeMillis();
        System.out.println(("请求时间：" + (ing - begin) + "ms"));
        return JSON.toJSONString(persons);
    }

    @RequestMapping("/findAllPerson")
    public String findAllPerson() {
        long begin = System.currentTimeMillis();
        List<Person> persons = personService.findAllPerson();
        long ing = System.currentTimeMillis();
        System.out.println(("请求时间：" + (ing - begin) + "ms"));
        return JSON.toJSONString(persons);
    }

    @RequestMapping("/findPage")
    public String findByPage() {
        Page<Person> persons = personService.findByPage(1, 2);
        // 需要把Page包装成PageInfo对象才能序列化。该插件也默认实现了一个PageInfo
        PageInfo<Person> pageInfo = new PageInfo<Person>(persons);
        return JSON.toJSONString(pageInfo);
    }

    @RequestMapping("/cacheFindAll")
    public String cacheByFindAll() {
        long begin = System.currentTimeMillis();
        List<Person> persons = personService.findAll();
        long ing = System.currentTimeMillis();
        personService.findAll();
        long end = System.currentTimeMillis();
        System.out.println(("第一次请求时间：" + (ing - begin) + "ms"));
        System.out.println(("第二次请求时间:" + (end - ing) + "ms"));
        return JSON.toJSONString(persons);
    }

    @RequestMapping("/cacheFindAllPerson")
    public String cacheFindAllPerson() {
        long begin = System.currentTimeMillis();
        List<Person> persons = personService.findAllPerson();
        long ing = System.currentTimeMillis();
        personService.findAllPerson();
        long end = System.currentTimeMillis();
        System.out.println(("第一次请求时间：" + (ing - begin) + "ms"));
        System.out.println(("第二次请求时间:" + (end - ing) + "ms"));
        return JSON.toJSONString(persons);
    }
}
