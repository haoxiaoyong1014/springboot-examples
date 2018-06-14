package com.haoxy.thymeleaf.repository;

import com.haoxy.thymeleaf.model.Message;

/**
 * Created by hxy on 2018/6/13.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public interface MessageRepository {

    Iterable<Message> findAll();

    Message save(Message message);

    Message findMessage(Long id);

    void deleteMessage(Long id);

}
