package cn.haoxiaoyong.thread.pool.boot;

/**
 * @author haoxiaoyong on 2020/4/8 下午 3:56
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
public interface ThreadService {

    /**
     * 执行异步操作
     */
    void executeAsync();

    /**
     * 执行异步操作 order
     */
    void executeAsyncByOrder();
}
