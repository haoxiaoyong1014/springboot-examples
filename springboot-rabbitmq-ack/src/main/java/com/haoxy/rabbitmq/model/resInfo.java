package com.haoxy.rabbitmq.model;

/**
 * Created by haoxiaoyong on 2018/7/2.
 */
public class ResInfo {

    private String  message;

    private Object context;

    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ResInfo{" +
                "message='" + message + '\'' +
                ", context=" + context +
                ", code=" + code +
                '}';
    }
}
