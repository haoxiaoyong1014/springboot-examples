package cn.haoxxiaoyong.elk.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haoxiaoyong
 * @date created at 下午5:05 on 2020/9/4
 * @github https://github.com/haoxiaoyong1014
 * @blog www.haoxiaoyong.cn
 */
@RestController
@Slf4j
public class ElkController {

    @RequestMapping("elk")
    public String testElk(String params) {

        log.info("接口入参 {}", params);

        params = "Hello World";

        log.error("error message {}", params);

        //int i = 1 / 0;

        return params;
    }
}
