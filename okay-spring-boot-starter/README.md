### 					从SpringBoot源码到自己封装一个Starter



这篇博客主要讲述一下springboot怎么给我们简化了大量的配置，然后跟着源码自己封装一个Starter,首先我们需要从两个地方来说，第一就是springboot的起步依赖，第二就是springboot自动装配；

#### 起步依赖

我们在创建一个springboot工程时需要引入`spring-boot-starter-web`这个依赖；

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

这个依赖我们点进去可以看到其实这个起步依赖集成了常用的web依赖,例如`spring-web`,`spring-webmvc`

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
  <version>2.1.4.RELEASE</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-json</artifactId>
  <version>2.1.4.RELEASE</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-tomcat</artifactId>
  <version>2.1.4.RELEASE</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.hibernate.validator</groupId>
  <artifactId>hibernate-validator</artifactId>
  <version>6.0.16.Final</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-web</artifactId>
  <version>5.1.6.RELEASE</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>org.springframework</groupId>
  <artifactId>spring-webmvc</artifactId>
  <version>5.1.6.RELEASE</version>
  <scope>compile</scope>
</dependency>
```

Spring Boot的起步依赖说白了就是对常用的依赖进行再一次封装，方便我们引入，简化了 pom.xml 配置，但是更重要的是将依赖的管理交给了 Spring Boot，我们无需关注不同的依赖的不同版本是否存在冲突的问题，Spring Boot 都帮我们考虑好了，我们拿来用即可！

在使用 Spring Boot 的起步依赖之前，我们需要在`pom.xml`中添加配置：

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.4.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

即让`pom.xml`继承 Spring Boot 的`pom.xml`，而 Spring Boot 的`pom.xml`里面定义了常用的框架的依赖以及相应的版本号,我们无需担心版本冲突问题；

#### 自动装配

首先我们知道springboot启动需要一个启动引导类，这个类除了是应用的入口之外，还发挥着配置的 Spring Boot 的重要作用。

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

可以看到`@SpringBootApplication`这个注解，我们点击进去这个注解，发现它发挥着多个注解的作用，这也体现了注解的派生性和层次性；

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)
public @interface SpringBootApplication {
    @AliasFor(
        annotation = EnableAutoConfiguration.class
    )
    Class<?>[] exclude() default {};
    
    //........
}
```

这里的`@SpringBootConfiguration`和`@ComponentScan`注解，前者其实就是`@Configuration`注解，就是起到声明这个类为配置类的作用，而后者起到开启自动扫描组件的作用。

我们重点分析一下`@EnableAutoConfiguration`这个注解，这个注解的作用就是开启Spring Boot 的自动装配功能，我们点进行看下：

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
    String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

    Class<?>[] exclude() default {};

    String[] excludeName() default {};
}
```

我们重点分析一下`@Import({AutoConfigurationImportSelector.class})`这个注解，我们知道`@Import`的作用是将组件添加到 Spring 容器中，而在这里即是将`AutoConfigurationImportSelector`这个组件添加到 Spring 容器中。也就是将`AutoConfigurationImportSelector`声明成一个Bean;

我们重点分析一下`@Import`注解中的`AutoConfigurationImportSelector`类；

```java
protected AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata,
			AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return EMPTY_ENTRY;
		}
		AnnotationAttributes attributes = getAttributes(annotationMetadata);
		List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
		configurations = removeDuplicates(configurations);
		Set<String> exclusions = getExclusions(annotationMetadata, attributes);
		checkExcludedClasses(configurations, exclusions);
		configurations.removeAll(exclusions);
		configurations = filter(configurations, autoConfigurationMetadata);
		fireAutoConfigurationImportEvents(configurations, exclusions);
		return new AutoConfigurationEntry(configurations, exclusions);
	}


protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
				getBeanClassLoader());
		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
				+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}
```

在`getAutoConfigurationEntry`方法中扫描`ClassPath`下的所有`jar`包的`spring.factories`文件，将`spring.factories`文件`key`为`EnableAutoConfiguration`的所有值取出，然后这些值其实是类的全限定名，**也就是自动配置类的全限定名**，然后 Spring Boot 通过这些全限定名进行类加载(反射)，将这些自动配置类添加到 Spring 容器中。

我们找到一个名为`spring-boot-autoconfigure-2.1.4.RELEASE.jar`的 jar 包，打开它的`spring.factories`文件，发现这个文件有`key`为`EnableAutoConfiguration`的键值对

<img src="https://upload-images.jianshu.io/upload_images/15181329-6cc01c6a6d313cbe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" alt="image.png" style="zoom:67%;" />

也就是这个`jar`包有自动配置类，可以发现这些自动配置配都是以`xxxAutoConfiguration`的命名规则来取名的，这些自动配置类包含我了们常用的框架的自动配置类，比如`aop`、`mongo`、`redis`和`web`等等，基本能满足我们日常开发的需求。例如我们程序中需要用到aop,直接引入相应的依赖即可！

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-aop</artifactId>
 </dependency>
```



我们取一个较为简单的配置类进行分析，看看是怎么发挥它的配置作用的；我们以`HttpEncodingAutoConfiguration`为例；部分代码如下：

```java
//声明这个类为配置类
@Configuration 
//开启ConfigurationProperties功能，同时将配置文件和HttpProperties.class绑定起来
@EnableConfigurationProperties({HttpProperties.class})
//只有在web应用下自动配置类才生效
@ConditionalOnWebApplication(
    type = Type.SERVLET
)
//只有存在CharacterEncodingFilter.class情况下 自动配置类才生效
@ConditionalOnClass({CharacterEncodingFilter.class})
//判断配置文件是否存在某个配置spring.http.encoding，如果存在其值为enabled才生效，如果不存在这个配置类也生效。
@ConditionalOnProperty(
    prefix = "spring.http.encoding",
    value = {"enabled"},
    matchIfMissing = true
)
public class HttpEncodingAutoConfiguration {
    private final Encoding properties;

    public HttpEncodingAutoConfiguration(HttpProperties properties) {
        this.properties = properties.getEncoding();
    }

    //将字符编码过滤器组件添加到 Spring 容器中
    @Bean
    //仅在该注解规定的类不存在于 spring容器中时,使用该注解的config或者bean声明才会被实例化到容器中
    @ConditionalOnMissingBean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.properties.getCharset().name());
        filter.setForceRequestEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpProperties.Encoding.Type.REQUEST));
        filter.setForceResponseEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpProperties.Encoding.Type.RESPONSE));
        return filter;
    }
    
    @Bean
public HttpEncodingAutoConfiguration.LocaleCharsetMappingsCustomizer localeCharsetMappingsCustomizer() {
    return new HttpEncodingAutoConfiguration.LocaleCharsetMappingsCustomizer(this.properties);
}
```

Configuration：这个注解声明了这个类为配置类(和我们平时写的配置类一样，同样是在类上加这个注解)。

EnableConfigurationProperties：开启`ConfigurationProperties`功能，也就是将配置文件和`HttpProperties.class`这个类绑定起来，将配置文件的相应的值和`HttpProperties.class`的变量关联起来，可以点击`HttpProperties.class`进去看看，

```java
@ConfigurationProperties(
    prefix = "spring.http"
)

public static final Charset DEFAULT_CHARSET;
private Charset charset;
private Boolean force;
private Boolean forceRequest;
private Boolean forceResponse;
private Map<Locale, Charset> mapping;
```

通过`ConfigurationProperties`指定前缀，将配置文件`application.properties`前缀为`spring.http`的值和`HttpProperties.class`的变量关联起来，通过类的变量可以发现，我们可以设置的属性是`charset`、`force`、`forceRequest`、`forceResponse`和`mapping`。另外`ConfigurationProperties`注解将`HttpProperties`类注入到Spring容器成为一个bean对象，因为一般来说，像springboot默认的包扫描路径为`xxxxxxApplication.java`所在包以及其所有子包,但是一些第三方的jar中的bean很明显不能被扫描到，此时该注解就派上了用场，当然，你可能会说，我使用`@ComponentScan`不就行了，这两个注解的区别是：`@ComponentScan`前提是你要的bean已经存在bean容器中了，而`@EnableConfigurationProperties`是要让容器自动去发现你要类并注册成为bean。也就是我们除了使用 Spring Boot 默认提供的配置信息之外，我们还可以通过配置文件指定配置信息。

- `ConditionalOnWebApplication:`这个注解的作用是自动配置类在 Web 应用中才生效。
- `ConditionalOnClass:`只有在存在`CharacterEncodingFilter`这个类的情况下自动配置类才会生效。
- `ConditionalOnProperty:`判断配置文件是否存在某个配置 spring.http.encoding ，如果存在其值为 enabled 才生效，如果不存在这个配置类也生效。
- `@ConditionalOnMissingBean: `仅在该注解规定的类不存在于 spring容器中时,使用该注解的config或者bean声明才会被实例化到容器中

可以发现后面几个注解都是`ConditionalXXXX`的命名规则，这些注解是 Spring 制定的条件注解，只有在符合条件的情况下自动配置类才会生效。

接下来的`characterEncodingFilter`方法，创建一个`CharacterEncodingFilter`的对象，也就是字符编码过滤器，同时设置相关属性，然后将对象返回，通过`@Bean`注解，将返回的对象添加到 Spring 容器中。这样字符编码过滤器组件配置好了，而平时的话，我们需要在 web.xml 进行如下配置：

```xml
 <filter>
       <filter-name>springUtf8Encoding</filter-name>
       <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
       <init-param>
           <param-name>encoding</param-name>
           <param-value>utf-8</param-value>
       </init-param>
       <init-param>
           <param-name>forceEncoding</param-name>
           <param-value>true</param-value>
       </init-param> 
    </filter>
    <filter-mapping>
       <filter-name>springUtf8Encoding</filter-name>
       <url-pattern>/*</url-pattern>
   </filter-mapping>
```

到这里原理我们已经分析完了，下面我们动手自己封装一个类似上面的`spring-boot-starter-aop`

#### 封装一个Starter

**1,SpringBoot Starter开发规范**

- 1、命名使用`spring-boot-starter-xxx`,其中`xxx`是我们具体的包名称，如果集成`Spring Cloud`则使用`spring-cloud-starter-xxx`
- 2、通常需要准备两个`jar`文件，其中一个不包含任何代码，只用于负责引入相关以来的jar文件，另外一个则包含核心的代码

如`nacos`与Spring Cloud集成的starter如下图：

<img src="https://user-gold-cdn.xitu.io/2019/8/23/16cbea9d2ead5e22?imageView2/0/w/1280/h/960/format/webp/ignore-error/1" style="zoom:80%;" />

更多`Starter`制作规范，我们可以查看[官网文档](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-custom-starter)

**2,Starter开发步骤**

我们创建一个名字为`okay-spring-boot-starter`的工程，并引入相关依赖：

```xml
	<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-autoconfigure</artifactId>
        </dependency>
    	<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
   </dependencies>
    <dependencyManagement>
        <!-- 我们是基于Springboot的应用 -->
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.4.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

因为我们需要用到Springboot提供的相关注解，并且使用springboot提供的自动配置功能，我们不得不引入`spring-boot-autoconfigure`和`spring-boot-dependencies`两个依赖。

**3,创建自动配置类**

一般来说，我们可能想在springboot启动的时候就预先注入自己的一些bean，此时，我们要新建自己的自动配置类，一般采用`xxxxAutoConfiguration`。这里就类似于上面的`HttpEncodingAutoConfiguration`，下面我们模仿`HttpEncodingAutoConfiguration`新建一个`OkayStarterAutoConfiguration`配置类；

```java
@Configuration
@EnableConfigurationProperties(OkayProperties.class)
@ConditionalOnClass(Okay.class)
@ConditionalOnWebApplication
public class OkayStarterAutoConfiguration {

    
    @Bean
    @ConditionalOnMissingBean
    /**
     * 当存在okay.config.enable=true的配置时,这个Okay bean才生效
     */
    @ConditionalOnProperty(prefix = "okay.config", name = "enable", havingValue = "true")
    public Okay defaultStudent(OkayProperties okayProperties) {
        Okay okay = new Okay();
        okay.setPlatform(okayProperties.getPlatform());
        okay.setChannel(okayProperties.getChannel());
        okay.setEnable(okayProperties.getEnable());
        return okay;
    }
}
```

这里每个注解的含义上面已经解释过了，这里就不做过多的解释；

新建一个`OkayProperties`,声明该starter的使用者可以配置哪些配置项。

```java
@ConfigurationProperties(prefix = "okay.config")
public class OkayProperties {

    private String platform;

    private String channel;

    private Boolean enable;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "OkayProperties{" +
                "platform='" + platform + '\'' +
                ", channel='" + channel + '\'' +
                ", enable=" + enable +
                '}';
    }
}

```

在`resources`目录下新建一个`META-INF`目录并且创建一个`spring.factories`文件

```json
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  cn.haoxiaoyong.okay.starter.config.OkayStarterAutoConfiguration
```

到这里是不是和上面我们讲解的源码基本一致！

#### 使用我们自己的Starter

新创建一个springboot工程，引入我们自己maven依赖：

```xml
	<dependency>
        <groupId>cn.haoxiaoyong.okay</groupId>
        <artifactId>okay-spring-boot-starter</artifactId>
        <version>0.0.2-SNAPSHO</version>
    </dependency>
```

并在配置文件appliaction.yml中配置

![](https://cg-mall.oss-cn-shanghai.aliyuncs.com/cg/images/_2.png)

你看多智能还会自动提示！

```yml
okay:
  config:
    platform: pdd
    channel: ws
    enable: true
```

```java
@RestController
@Slf4j
public class OkController {

    @Autowired
    Okay okay;

    @RequestMapping("okay")
    public String testOkay() {
        log.info(okay.getChannel() + "  " + okay.getPlatform() + "  " + okay.getEnable());

        return okay.getChannel() + "  " + okay.getPlatform() + "  " + okay.getEnable();
    }
}
```

浏览器输入：localhost:8082/okay,控制台打印：

<img src="https://upload-images.jianshu.io/upload_images/15181329-9defb376b159b72a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" alt="image.png" style="zoom:80%;" />

这个例子只是展示一下逻辑效果，这篇[使用自定义Starter 并制作一个简单的图床](https://juejin.im/post/5e84a2fa51882573793e6ae6)