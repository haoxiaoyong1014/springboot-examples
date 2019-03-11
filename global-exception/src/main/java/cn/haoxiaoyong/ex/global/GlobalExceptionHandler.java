package cn.haoxiaoyong.ex.global;

import cn.haoxiaoyong.ex.common.RespInfo;
import cn.haoxiaoyong.ex.exception.CustomizeException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by haoxy on 2019/3/11.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理系统异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String systemErrorHandler(Exception ex) {
        RespInfo respInfo = new RespInfo();
        respInfo.setCode(400);
        respInfo.setMsg("系统异常");
        respInfo.setData(ex.getMessage());
        return JSONObject.toJSONString(respInfo);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = CustomizeException.class)
    @ResponseBody
    public String customizeException(CustomizeException ce) {
       return ce.getContent();
    }

}
