package com.haoxy.rabbitmq.model;

import java.io.Serializable;

/**
 * Created by hxy on 2018/7/3.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class ResponseEntity implements Serializable {

    private static final long serialVersionUID = -7715678696640699601L;
    private int statusCode;
    private String msg;
    private Object object;


    private ResponseEntity(int statusCode, String msg, Object object) {
        this.statusCode = statusCode;
        this.msg = msg;
        this.object = object;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public Object getObject() {
        return object;
    }

    public static ResponseEntity ok() {
        return new ResponseEntity(200, "success", "");

    }

    @Override
    public String toString() {
        return "{" +
                "statusCode:" + statusCode +
                ", msg:" + msg +
                ", object:" + object +
                '}';
    }
}
