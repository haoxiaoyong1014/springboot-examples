package cn.haoxiaoyong.thread.pool.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

/**
 * @author haoxiaoyong on 2020/4/8 下午 3:57
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */

@Service
public class ThreadServiceImpl implements ThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ThreadServiceImpl.class);

    @Autowired
    private ExecutorService userThreadPool;

    @Autowired
    private ExecutorService orderThreadPool;

    @Override
    @Async(value = "userThreadPool")
    public void executeAsync() {
        logger.info("start executeAsync user");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync user");
    }

    @Override
    public void executeAsyncByOrder() {
        logger.info("start executeAsync order ");
        orderThreadPool.execute(new OrderQueueThread());
        logger.info("end executeAsync order");
    }

}
