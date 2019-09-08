package cn.haoxy.strategy.aop.service;

import cn.haoxy.strategy.aop.pojo.AnalysisUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 11:23
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Service
public class AnalysisUserService {

    private static List<AnalysisUser> list = new ArrayList<>();

    public AnalysisUser selectById(Long id) {
        //这里模拟查询
        AnalysisUser analysisUser = list.get(Math.toIntExact(id));
        return analysisUser;
    }

    public String addUser(Long roleId, AnalysisUser user) {
        AnalysisUser analysisUser = new AnalysisUser();
        analysisUser.setId(1L);
        analysisUser.setUsername(user.getUsername());
        list.add(analysisUser);
        return "success";
    }

    public String delUser(Long id) {
        return "删除成功";
    }
}
