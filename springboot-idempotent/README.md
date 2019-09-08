### springboot + redis + 注解 + 拦截器 实现接口幂等性校验

#### 一、概念
幂等性, 通俗的说就是一个接口, 多次发起同一个请求, 必须保证操作只能执行一次
比如:

* 订单接口, 不能多次创建订单

* 支付接口, 重复支付同一笔订单只能扣一次钱

* 支付宝回调接口, 可能会多次回调, 必须处理重复回调

* 普通表单提交接口, 因为网络超时等原因多次点击提交, 只能成功一次 等等

#### 二、常见解决方案
* 唯一索引 -- 防止新增脏数据

* token机制 -- 防止页面重复提交

* 悲观锁 -- 获取数据的时候加锁(锁表或锁行)

* 乐观锁 -- 基于版本号version实现, 在更新数据那一刻校验数据

* 分布式锁 -- redis(jedis、redisson)或zookeeper实现

* 状态机 -- 状态变更, 更新数据时判断状态

#### 三、本文实现
本文采用第2种方式实现, 即通过redis + token机制实现接口幂等性校验

#### 四、实现思路
为需要保证幂等性的每一次请求创建一个唯一标识 token, 先获取 token, 并将此 token存入redis, 请求接口时, 将此 token放到header或者作为请求参数请求接口, 后端接口判断redis中是否存在此 token:

* 如果存在, 正常处理业务逻辑, 并从redis中删除此 token, 那么, 如果是重复请求, 由于 token已被删除, 则不能通过校验, 返回 请勿重复操作提示

* 如果不存在, 说明参数不合法或者是重复请求, 返回提示即可

#### 五、项目简介
* springboot

* redis

* @ApiIdempotent注解 + 拦截器对请求进行拦截

* @ControllerAdvice全局异常处理

* 压测工具: jmeter

#### 六、代码实现

```xml
<dependencies>
   <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
       <version>1.16.22</version>
    </dependency>
        <!-- Redis-Jedis -->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
         <version>2.9.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.4</version>
     </dependency>
</dependencies>
```

JedisUtil

```java
@Component
@Slf4j
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 设值
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key: {} value: {} error", key, value, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 设值
     *
     * @param key
     * @param value
     * @param expireTime 过期时间, 单位: s
     * @return
     */
    public String set(String key, String value, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setex(key, expireTime, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} expireTime:{} error", key, value, expireTime, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 设值
     *
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setnx(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 取值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            log.error("del key:{} error", key, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            log.error("exists key:{} error", key, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 设值key过期时间
     *
     * @param key
     * @param expireTime 过期时间, 单位: s
     * @return
     */
    public Long expire(String key, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expire(key.getBytes(), expireTime);
        } catch (Exception e) {
            log.error("expire key:{} error", key, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    /**
     * 获取剩余时间
     *
     * @param key
     * @return
     */
    public Long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error("ttl key:{} error", key, e);
            return null;
        } finally {
            close(jedis);
        }
    }

    private void close(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }
}
```

自定义注解 @ApiIdempotent

```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiIdempotent {
    
}
```

ApiIdempotentInterceptor拦截器

```java
public class ApiIdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);
        if (methodAnnotation != null) {
            check(request);// 幂等性校验, 校验通过则放行, 校验失败则抛出异常, 并通过统一异常处理返回友好提示
        }

        return true;
    }

    private void check(HttpServletRequest request) {
        tokenService.checkToken(request);
    }
}

```

TokenServiceImpl

```java
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public ServerResponse createToken() {
        String str = RandomUtil.UUID32();
        StrBuilder token = new StrBuilder();
        token.append(Constant.Redis.TOKEN_PREFIX).append(str);

        jedisUtil.set(token.toString(), token.toString(), Constant.Redis.EXPIRE_TIME_HOUR);

        return ServerResponse.success(token.toString());
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!jedisUtil.exists(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }
}

```

TestApplication

```java
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
```

OK, 目前为止, 校验代码准备就绪, 接下来测试验证

七、测试验证

获取 token的控制器 TokenController

```java
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("token")
    public ServerResponse token(){
        return tokenService.createToken();
    }

}
```

TestController, 注意 @ApiIdempotent注解, 在需要幂等性校验的方法上声明此注解即可, 不需要校验的无影响

```java
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @ApiIdempotent
    @PostMapping("testIdempotence")
    public ServerResponse testIdempotence(){
        return testService.testIdempotence();
    }
}
```
获取 token

![image.png](https://upload-images.jianshu.io/upload_images/15181329-857f4c3a3f160509.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

测试接口安全性: 利用jmeter测试工具模拟50个并发请求, 将上一步获取到的token作为参数

![image.png](https://upload-images.jianshu.io/upload_images/15181329-0be95e54d3e6fd55.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/15181329-4a40a550f4bb5beb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 八、注意点(非常重要)

![image.png](https://upload-images.jianshu.io/upload_images/15181329-7634c302a1e9a4dd.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

上图中, 不能单纯的直接删除token而不校验是否删除成功, 会出现并发安全性问题, 因为, 有可能多个线程同时走到第46行, 此时token还未被删除, 所以继续往下执行, 如果不校验 jedisUtil.del(token)的删除结果而直接放行, 那么还是会出现重复提交问题, 即使实际上只有一次真正的删除操作, 下面重现一下

稍微修改一下代码:

![image.png](https://upload-images.jianshu.io/upload_images/15181329-2dca38145aecb8b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

再次请求

![image.png](https://upload-images.jianshu.io/upload_images/15181329-f054aef96c34c90c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


![image.png](https://upload-images.jianshu.io/upload_images/15181329-7a0710a56d3fe6f3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

再看看控制台

![image.png](https://upload-images.jianshu.io/upload_images/15181329-1e345a152383be97.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

虽然只有一个真正删除掉token, 但由于没有对删除结果进行校验, 所以还是有并发问题, 因此, 必须校验

#### 九、总结
其实思路很简单, 就是每次请求保证唯一性, 从而保证幂等性, 通过拦截器+注解, 就不用每次请求都写重复代码, 其实也可以利用spring aop实现。