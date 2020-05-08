package cn.haoxiaoyong.thread.pool.boot;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.*;

/**
 * @author haoxiaoyong on 2020/4/8 下午 3:25
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 用户队列线程
     * @return
     */
    @Bean(value = "userThreadPool")
    public ExecutorService buildUserQueueThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
               /* .setNameFormat("user-thread-%d")*/.build();

        ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5),namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());

        return pool ;
    }

    /**
     * 订单
     * @return
     */
    @Bean(value = "orderThreadPool")
    public ExecutorService buildOrderQueueThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                /*.setNameFormat("order-thread-%d")*/.
                        build();

        ExecutorService pool = new ThreadPoolExecutor(5, 6, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(3),namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());

        return pool ;
    }

}
