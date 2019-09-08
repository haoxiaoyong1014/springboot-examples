package cn.haoxy.strategy.aop.handler;

import cn.haoxy.strategy.aop.strategys.StrategyBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:14
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Component
public class DataSourceContextAware {

    @Autowired
    private final Map<String, StrategyBase> strategyMap = new ConcurrentHashMap<>(3);

    public StrategyBase getStrategyInstance(String dsType) {
        StrategyBase strategyBase = strategyMap.get(dsType);
        return strategyBase;
    }
}
