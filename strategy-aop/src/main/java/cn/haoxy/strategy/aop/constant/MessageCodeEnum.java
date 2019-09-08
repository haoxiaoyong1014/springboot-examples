package cn.haoxy.strategy.aop.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:12
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum  MessageCodeEnum {

    USER_ADD("添加了账号"),

    USER_LOGIN("登录系统"),

    USER_LOGK("禁用了账号"),

    USER_UNLOCK("启用了账号"),

    USER_DEL("删除了账号"),

    DEL_ROLE("删除了角色"),

    EDIT_ROLE("修改了角色")

    ;
    private String msg;
}
