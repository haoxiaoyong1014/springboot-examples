package cn.haoxiaoyong.thread.pool.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author haoxiaoyong on 2020/4/8 下午 3:34
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@RestController
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ThreadService threadService;


    @RequestMapping("user")
    public String submitUser(){
        logger.info("start submit user");

        //调用service层的任务
        threadService.executeAsync();

        logger.info("end submit user");

        return "success";
    }

    @RequestMapping("order")
    public String submitOrder(){
        logger.info("start submit order");

        //调用service层的任务
        threadService.executeAsyncByOrder();

        logger.info("end submit order");

        return "success";
    }
}
