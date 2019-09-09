### AOP+自定义注解+策略模式 记录操作日志，避免过多的 if else

首先看下业务需求：
![image.png](https://upload-images.jianshu.io/upload_images/15181329-9d307e9e9bdf6290.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
如图所示，就是将用户的操作行为记录到日志表中,而且有些内容是动态的，如图是六种操作，但是后期肯定是要增加的，也就是说就这六种需要记录到日志表中，日志表所对应的对象是`AnalysisMessage`,标题，内存，接收时间，对应对象中字段名为`title`,`content`,`create_time`；看到需求的第一眼就想到AOP来实现；但是只是使用AOP的会写一些if else,后期如果要增加一些是不是要增加if else,后期维护起来是相当麻烦，我就想到了使用策略模式；

这里有个前提就是必须这些操作是成功之后才可以入库，所以我选择了使用`@Around`，在切入点前后切入内容;因为这样可以在调用接口结束之后拿到接口返回的参数，从而判断接口是否调用成功；下面就用代码来实现一下

首先我会将这个操作以`key` `value` 的形式存放到json文件中，以url 为key,操作title为value;
在项目的`resources`目录下有个test.json文件，内容如下：
```json
{
  "/user/addUser": "账号添加",
  "/user/isLock": "账号禁用",
  "/user/delUser": "账号删除"
}
```
这里只列举三种操作，之所以存放到json文件中，目的是想让本案例更简化，不想涉及到数据库，所以这个演示案例只放到了json文件中；在生成环境中是配置在数据库中的；
在项目启动的时候将json文件中的内容以`key` `value` 的形式加载到map中；
代码实现如下：
```java
@Component
@Slf4j
public class MessageInitHandler implements CommandLineRunner {

    /**
     * 文件名
     */
    private static String fileName = "test.json";

    /**
     * 初始化 Map
     */
    private Map<String, String> initMap = Maps.newHashMap();

    @Override
    public void run(String... args) throws Exception {

        JSONObject jsonObject = loadingJSONFile();
        log.info("json Data are as follows:{}", jsonObject);
       //使用Gson将json转成map
        Gson gson = new Gson();
        initMap = gson.fromJson(jsonObject.toJSONString(), Map.class);
       //将initMap赋值给MapCacheUtils.mapCaheInit
        MapCacheUtils.mapCaheInit = initMap;
    }

    private JSONObject loadingJSONFile() {

        log.info("开始加载resources/test.json");

        Enumeration<URL> resources;
        JSONObject jsonObject = new JSONObject();
        try {
            resources = getClassLoader().getResources(fileName);
        } catch (IOException e) {
            log.warn("getJsonResource fail {}", fileName, e);
            return jsonObject;
        }
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            try {
                String json = Resources.toString(url, Charsets.UTF_8);
                jsonObject.putAll(JSON.parseObject(json)); // 有多个的时候，后面的覆盖前面的
            } catch (IOException e) {
                log.warn("addJsonFile fail url:{}", url, e);
            }
        }
        return jsonObject;
    }

    private static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            return classLoader;
        }
        return MessageInitHandler.class.getClassLoader();
    }
}
```
MessageIntiHandler实现CommandLineRuner,并实现run方法；
这样MapCacheUtils.mapCaheInit中就有三条数据；初始化工作完成；

然后自定义一个注解`@MessageLog`,标注在需要aop拦截的接口上；也就是上图中需要存库的操作接口上；例如 账号添加，账号删除...等
```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageLog {
}
```
然后编写接口：包括账号添加，账号删除，等。。接口，并标识MessageLog注解，


```java
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
```
**然后编写策略类**
首先 定义个`StrategyBase`接口
```java
//策略父类
public interface StrategyBase {
    
    String run(Object[] args);

}
```
接下来就是`StrategyBase`的子类：

**账号添加策略**
```java
@Component(value="/user/addUser")
public class MessageAddUserStrategy implements StrategyBase {

    @Autowired
    private AnalysisRoleService analysisRoleService;
    @Override
    public String run(Object[] args) {
        Long roleId = null;
        AnalysisUser analysisUser = null;
        for (Object arg : args) {
            if (arg instanceof Long) {
                roleId = (Long) arg;
            } else if (arg instanceof AnalysisUser) {
                analysisUser = (AnalysisUser) arg;
            } else {
                return null;
            }
        }
        String username = analysisUser.getUsername();
        AnalysisRole role = analysisRoleService.findByRoleId(roleId);
       return "添加了账号" + username + "(" + role.getName() + ")";
    }
}
```
**账号删除策略**
```java
@Component("/user/delUser")
public class MessageDelUserStrategy implements StrategyBase {

    @Override
    public String run(Object[] args) {

        for (Object arg : args) {
            if (arg instanceof AnalysisUser) {
                AnalysisUser analysisUser = (AnalysisUser) arg;
              return "删除了账号" + analysisUser.getUsername() + "(" + analysisUser.getRealname() + ")";
            }
        }
        return null;
    }
}
```
**禁用账号策略**

```java
@Component("/user/isLock")
public class MessageIsLockStrategy implements StrategyBase {

    @Autowired
    private AnalysisUserService analysisUserService;

    @Override
    public String run(Object[] args) {
        AnalysisUser analysisUser = null;
        for (Object arg : args) {
            if (arg instanceof AnalysisUser) {
                analysisUser = (AnalysisUser) arg;
                if (analysisUser.getStatus().equals((byte) 1)) {
                    analysisUser = analysisUserService.selectById(analysisUser.getId());
                    return "启用了账号" + analysisUser.getUsername() + " (" + analysisUser.getRealname() + ")";
                } else if (analysisUser.getStatus().equals((byte) 2)) {
                    analysisUser = analysisUserService.selectById(analysisUser.getId());
                    return "禁用了账号" + analysisUser.getUsername() + " (" + analysisUser.getRealname() + ")";
                }
            }
        }
        return null;
    }
}
```
这里稍微的注意一下`@Component`注解中的value值，

**策略控制器**
```java
@Component
public class DataSourceContextAware {

    @Autowired
    private final Map<String, StrategyBase> strategyMap = new ConcurrentHashMap<>(3);

    public StrategyBase getStrategyInstance(String dsType) {
        StrategyBase strategyBase = strategyMap.get(dsType);
        return strategyBase;
    }
}
```
这里定义一个`ConcurrentHashMap`,这个类的作用就是将策略名(`@Component`注解中的value值),和实现StrategyBase的类，以`key,value `的形式保存到了`ConcurrentHashMap`中；

到了这里`@MessageLog`注解只是定义出来了，还没有正式的用上，下面该轮到`@MessageLog`和`aop`上场了。

**定义切面**

* 使用`@Aspect`注解将一个java类定义为切面类
* 使用`@Pointcut`定义一个切入点，可以是一个规则表达式，比如某个package下的所有函数，也可以是一个注解等。
* 根据需要在切入点不同位置的切入内容
   * 使用@Before在切入点开始处切入内容
   * 使用@After在切入点结尾处切入内容
   * 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
  * 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
  * 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑

上面也说到了，就是必须这些操作是成功之后才可以入库，所以我选择了使用@Around，在切入点前后切入内容;因为这样可以在调用接口结束之后拿到接口返回的参数，从而判断接口是否调用成功；

```java
@Aspect
@Component
public class MessageMonitorHandler {

    private Logger logger = LoggerFactory.getLogger(MessageMonitorHandler.class);

    @Autowired
    private AnalysisMessageService messageService;

    @Autowired
    private MessageStrategyService messageStrategyService;
    
    @Autowired
    private StringHttpMessageConverter converter;


    @Pointcut("@annotation(cn.haoxy.strategy.aop.annotation.MessageLog)")
    public void checkMessageHandler() {

    }

    @Around("checkMessageHandler()")
    public void doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("start run doAround.....");
        Object obj = proceedingJoinPoint.proceed();//调用执行目标方法
        //返回客户端结果
        HttpServletResponse response = getHttpServletResponse();
        HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
        //converter.write(obj, MediaType.APPLICATION_JSON, outputMessage);
        converter.write(obj.toString(),null, outputMessage);
        shutdownResponse(response);
        
        //判断调用是否成功
        //省略判断  ......
        //如果调用成功
        processOutPutObj(proceedingJoinPoint);
    }

    private void processOutPutObj(ProceedingJoinPoint proceedingJoinPoint) {

       Object[] args = proceedingJoinPoint.getArgs();
        //得到HttpServletRequest
        HttpServletRequest request = getHttpServletRequest();
        //得到请求url
        String url = request.getServletPath();
        //根据url从MapCacheUtils.mapCaheInit中取出操作title,
        // 这里是从test.json文件中读取的，当然也可以配置在数据库中
        String operatorLog = MapCacheUtils.mapCaheInit.get(url);
        //根据url取出对应的策略类,这里的url也就是和策略类上@Component注解的value值
        StrategyBase messageChild = messageStrategyService.run(url);
        //拿到策略类执行相应的策略方法
        String content = messageChild.run(args);
        AnalysisMessage analysisMessage = new AnalysisMessage();
        analysisMessage.setId(1L);
        analysisMessage.setTitle(operatorLog);
        analysisMessage.setContent(content);
        //在这里模拟存库
        messageService.insert(analysisMessage);

       logger.info("  end  run doAround....." + content);
    }


    /**
     * 获取 HttpServletRequest
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
    
      /**
       * 获取 HttpServletResponse
       */
        private HttpServletResponse getHttpServletResponse() {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
            return servletRequestAttributes.getResponse();
        }
    
        /**
         * 关流
         * @param response
         * @throws IOException
         */
        private void shutdownResponse(HttpServletResponse response) throws IOException {
            response.getOutputStream().close();
        }
    
}

```
这里`@Pointcut`是使用注解的方式；在检测接口上含有`@MessageLog`注解时就会被AOP拦截；

这里贴一下`MessageStrategyService`类：
```java
@Component
public class MessageStrategyService {

    @Autowired
    private DataSourceContextAware dataSourceContextAware;

    public StrategyBase run(String dsType) {
         //这里调用策略控制器中的getStrategyInstance方法，来获取对应的策略类
        StrategyBase strategyInstance = dataSourceContextAware.getStrategyInstance(dsType);
        return strategyInstance;
    }
}
```
**下面进行测试**

使用postman 测试账号添加：
![image.png](https://upload-images.jianshu.io/upload_images/15181329-d83d8233816e084c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

账号添加测试结果打印：

![image.png](https://upload-images.jianshu.io/upload_images/15181329-896753ba2e0bdc22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

账号删除测试结果打印：

![image.png](https://upload-images.jianshu.io/upload_images/15181329-2c977e32c1219f47.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


打印content结果缺少主语（当前登陆的用户），需求是：xxx添加了账号xxx(角色名)；这里有很多种方式可以拿到当前用户，如果使用token的话，可以从token中解析出当前用户的id,我这里使用的是shrio,从而也很方便的拿到当前用户，为了减少本演示案例的复杂度就没去引入，这里只突出主要部分；