package cn.haoxy.strategy.aop.strategys;

import cn.haoxy.strategy.aop.constant.MessageCodeEnum;
import cn.haoxy.strategy.aop.pojo.AnalysisUser;
import cn.haoxy.strategy.aop.service.AnalysisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:27
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Component("/user/isLock")
public class MessageIsLockStrategy implements StrategyBase {

    @Autowired
    private AnalysisUserService analysisUserService;

    @Override
    public String run(Object[] args) {
        AnalysisUser analysisUser = null;
        for (Object arg : args) {
            if (arg instanceof AnalysisUser) {
                analysisUser = (AnalysisUser) arg;
                if (analysisUser.getStatus().equals((byte) 1)) {
                    analysisUser = analysisUserService.selectById(analysisUser.getId());
                    return MessageCodeEnum.USER_UNLOCK.getMsg() + analysisUser.getUsername() + " (" + analysisUser.getRealname() + ")";
                } else if (analysisUser.getStatus().equals((byte) 2)) {
                    analysisUser = analysisUserService.selectById(analysisUser.getId());
                    return MessageCodeEnum.USER_LOGK.getMsg() + analysisUser.getUsername() + " (" + analysisUser.getRealname() + ")";
                }
            }
        }
        return null;
    }
}
