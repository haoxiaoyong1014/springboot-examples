package com.haoxy.example.service;

import com.github.pagehelper.Page;
import com.haoxy.example.model.Person;

import java.util.List;

/**
 * Created by hxy on 2018/6/27.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface PersonService {

    List<Person> findAll();

    /**
     * 分页查询
     * @param pageNo 页号
     * @param pageSize 每页显示记录数
     * @return
     */
    Page<Person> findByPage(int pageNo, int pageSize);

    void insert(Person person);


    void uptate(Long id);

    Person findById(Long id);

    int uptatePerson(Person person1);

    List<Person> findAllPerson();
}
