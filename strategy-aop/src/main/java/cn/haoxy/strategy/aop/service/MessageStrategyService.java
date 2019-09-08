package cn.haoxy.strategy.aop.service;

import cn.haoxy.strategy.aop.handler.DataSourceContextAware;
import cn.haoxy.strategy.aop.strategys.StrategyBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:46
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Component
public class MessageStrategyService {

    @Autowired
    private DataSourceContextAware dataSourceContextAware;

    public StrategyBase run(String dsType) {
        //这里调用策略控制器中的getStrategyInstance方法，来获取对应的策略子类
        StrategyBase strategyInstance = dataSourceContextAware.getStrategyInstance(dsType);
        return strategyInstance;
    }
}
