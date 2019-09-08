package cn.haoxy.strategy.aop.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by haoxiaoyong on 2019/9/8 下午 5:04
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
public class ReturnUtils {

    public static void infoReturn(HttpServletResponse response, Object obj) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            out = response.getWriter();
            JSONObject object = new JSONObject();
            object.put("msg", obj);
            out.print(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}
