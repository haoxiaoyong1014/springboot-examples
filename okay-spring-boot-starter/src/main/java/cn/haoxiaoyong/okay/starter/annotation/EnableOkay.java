package cn.haoxiaoyong.okay.starter.annotation;

import cn.haoxiaoyong.okay.starter.config.OkayProperties;
import cn.haoxiaoyong.okay.starter.config.OkayStarterAutoConfiguration;
import cn.haoxiaoyong.okay.starter.model.Okay;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author haoxiaoyong on 2020/3/20 下午 4:05
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigurationProperties(OkayProperties.class)
@ConditionalOnWebApplication
@Import(OkayStarterAutoConfiguration.class)
public @interface EnableOkay {

}
