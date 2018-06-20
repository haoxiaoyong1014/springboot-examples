package cn.merryyou.security.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hxy on 2018/6/20
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@Configuration
@EnableConfigurationProperties(OAuth2Properties.class)
public class OAuth2CoreConfig {
}
