package cn.haoxy.strategy.aop.service;

import cn.haoxy.strategy.aop.pojo.AnalysisRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:55
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Service
public class AnalysisRoleService {

    private static List<AnalysisRole> roles;

    static {
        roles = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            AnalysisRole analysisRole = new AnalysisRole();
            analysisRole.setId((long) i);
            analysisRole.setName("管理员");
            roles.add(analysisRole);
        }
    }

    public AnalysisRole findByRoleId(Long roleId) {

        AnalysisRole analysisRole = roles.get(Math.toIntExact(roleId));
        return analysisRole;
    }


}
