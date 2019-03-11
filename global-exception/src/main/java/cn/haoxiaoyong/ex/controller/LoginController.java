package cn.haoxiaoyong.ex.controller;

import cn.haoxiaoyong.ex.common.Parameter;
import cn.haoxiaoyong.ex.exception.CustomizeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haoxy on 2019/3/11.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@RestController
@RequestMapping(value = "login")
public class LoginController {

    @RequestMapping(value = "in")
    public String login(@RequestBody Parameter parameter) {

        if (StringUtils.isAnyBlank(parameter.getPassword(), parameter.getPhone())) {
            throw new CustomizeException(1001, "必要参数不能为空");
        }
        int i = 1 / 0;

        if (!"1111".equals(parameter.getPhone()) || !"1234".equals(parameter.getPassword())) {
            throw new CustomizeException(1002, "参数错误");
        }
        return "登录成功";
    }
}
