package cn.haoxy.strategy.aop.strategys;

import cn.haoxy.strategy.aop.constant.MessageCodeEnum;
import cn.haoxy.strategy.aop.pojo.AnalysisRole;
import cn.haoxy.strategy.aop.pojo.AnalysisUser;
import cn.haoxy.strategy.aop.service.AnalysisRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:24
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Component("/user/addUser")
public class MessageAddUserStrategy implements StrategyBase {

    @Autowired
    private AnalysisRoleService analysisRoleService;
    @Override
    public String run(Object[] args) {
        Long roleId = null;
        AnalysisUser analysisUser = null;
        for (Object arg : args) {
            if (arg instanceof Long) {
                roleId = (Long) arg;
            } else if (arg instanceof AnalysisUser) {
                analysisUser = (AnalysisUser) arg;
            } else {
                return null;
            }
        }
        String username = analysisUser.getUsername();
        AnalysisRole role = analysisRoleService.findByRoleId(roleId);
        return MessageCodeEnum.USER_ADD.getMsg() + username + "(" + role.getName() + ")";
    }
}
