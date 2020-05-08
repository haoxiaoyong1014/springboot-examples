package cn.haoxiaoyong.thread.pool.boot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author haoxiaoyong on 2020/5/8 上午 10:15
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
public class OrderQueueThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(OrderQueueThread.class);

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            logger.info("start run...");
            System.out.println("order thread do something......");
            logger.info("end run...");
        }
    }
}
