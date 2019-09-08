package cn.haoxy.strategy.aop.service;

import cn.haoxy.strategy.aop.pojo.AnalysisMessage;
import org.springframework.stereotype.Service;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:49
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Service
public class AnalysisMessageService {

    public void insert(AnalysisMessage analysisMessage) {
        //存库。。。。。
        System.out.println("存库成功,数据为: " + analysisMessage.toString());
    }

}
