package cn.haoxy.strategy.aop.strategys;

import cn.haoxy.strategy.aop.constant.MessageCodeEnum;
import cn.haoxy.strategy.aop.pojo.AnalysisUser;
import org.springframework.stereotype.Component;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:26
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Component("/user/delUser")
public class MessageDelUserStrategy implements StrategyBase {

    @Override
    public String run(Object[] args) {

        for (Object arg : args) {
            if (arg instanceof AnalysisUser) {
                AnalysisUser analysisUser = (AnalysisUser) arg;
                return MessageCodeEnum.USER_DEL.getMsg() + analysisUser.getUsername() + "(" + analysisUser.getRealname() + ")";
            }
        }
        return null;
    }
}
