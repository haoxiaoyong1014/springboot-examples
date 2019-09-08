package cn.haoxy.redis.token;

import cn.haoxy.redis.token.interceptor.ApiIdempotentInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Haoxy
 * Created in 2019-08-15.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */
@SpringBootApplication
public class TokenApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(TokenApplication.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口幂等性拦截器
        registry.addInterceptor(apiIdempotentInterceptor())
        .addPathPatterns("/**");
    }

    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor() {
        return new ApiIdempotentInterceptor();
    }
}
