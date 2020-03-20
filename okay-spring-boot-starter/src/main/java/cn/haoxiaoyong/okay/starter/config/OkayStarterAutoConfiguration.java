package cn.haoxiaoyong.okay.starter.config;

import cn.haoxiaoyong.okay.starter.model.Okay;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author haoxiaoyong on 2020/3/20 上午 10:48
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Configuration
@EnableConfigurationProperties(OkayProperties.class)
@ConditionalOnClass(Okay.class)
@ConditionalOnWebApplication
public class OkayStarterAutoConfiguration {

    /**
     * 当存在okay.config.enable=true的配置时,这个Okay bean才生效
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "okay.config", name = "enable", havingValue = "true")
    public Okay defaultStudent(OkayProperties okayProperties) {
        Okay okay = new Okay();
        okay.setPlatform(okayProperties.getPlatform());
        okay.setChannel(okayProperties.getChannel());
        okay.setEnable(okayProperties.getEnable());
        return okay;
    }
}
