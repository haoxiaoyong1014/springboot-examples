package cn.haoxy.strategy.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:09
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageLog {
}
