package com.haoxy.example.mapper;

import com.github.pagehelper.Page;
import com.haoxy.example.model.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by hxy on 2018/6/27.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Mapper
public interface PersonMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<Person> findAll();

    /**
     * 分页查询数据
     *
     * @return
     */
    Page<Person> findByPage();

    List<Person> findAllPerson();

}
