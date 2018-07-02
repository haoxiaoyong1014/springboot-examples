package com.haoxy.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.haoxy.example.mapper.PersonMapper;
import com.haoxy.example.model.Person;
import com.haoxy.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hxy on 2018/6/27.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;
    @Override
    public List<Person> findAll() {
        return personMapper.findAll();
    }

    @Override
    public Page<Person> findByPage(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return personMapper.findByPage();
    }

    @Override
    @Transactional
    public void insert(Person person) {
        personMapper.insert(person);
    }

    @Override
    public void uptate(Long id) {

    }

    @Override
    public Person findById(Long id) {

        return personMapper.selectByPrimaryKey(id);
    }

    @Override
    public int uptatePerson(Person person1) {

        return personMapper.updateByPrimaryKeySelective(person1);
    }

    @Override
    public List<Person> findAllPerson() {

        return personMapper.findAllPerson();
    }


}
