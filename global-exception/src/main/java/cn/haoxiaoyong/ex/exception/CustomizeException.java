package cn.haoxiaoyong.ex.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by haoxy on 2019/3/11.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
public class CustomizeException extends RuntimeException {


    private String content;


    public CustomizeException() {
    }


    public CustomizeException(int code, String msg) {
        JSONObject returnJson = new JSONObject();
        returnJson.put("code", code);
        returnJson.put("msg", msg);
        this.setContent(returnJson.toJSONString());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
