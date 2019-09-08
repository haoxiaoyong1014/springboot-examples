package cn.haoxy.strategy.aop.controller;

import cn.haoxy.strategy.aop.annotation.MessageLog;
import cn.haoxy.strategy.aop.pojo.AnalysisUser;
import cn.haoxy.strategy.aop.service.AnalysisUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 11:29
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private AnalysisUserService analysisUserService;

    /**
     * 添加用户
     */
    @RequestMapping("addUser")
    @MessageLog
    public String addUser(@RequestParam("roleId") Long roleId, AnalysisUser user){
        return analysisUserService.addUser(roleId,user);
    }

    /**
     * 删除用户
     * 需要参数：userid username realname
     * 因为这里把用户删除之后在策略类中就查询不到该用户的信息
     */
    @RequestMapping("delUser")
    @MessageLog
    public String delUser(@RequestBody AnalysisUser analysisUser){
        return analysisUserService.delUser(analysisUser.getId());
    }

    /**
     * 锁定用户这里就不模拟了。。。
     */
}
