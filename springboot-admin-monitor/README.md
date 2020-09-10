#### springBoot-admin 日志,系统监控

**springboot-admin-server**
```xml
<dependencies>
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-server</artifactId>
            <version>2.1.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
    </dependencies>
```
```yaml
spring:
    application:
        name: admin-server
    security:
        user:
            name: admin
            password: admin
server:
    port: 8000
```


**springboot-admin-client**

```xml
        <dependency>
            <groupId>de.codecentric</groupId>
            <artifactId>spring-boot-admin-starter-client</artifactId>
            <version>2.1.0</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
```
```yaml
spring:
    application:
        name: admin-client
    boot:
        admin:
            client:
                url: http://localhost:8000
                username: admin
                password: admin
server:
    port: 8001

management:
    endpoints:
        web:
            exposure:
                include: '*'
    endpoint:
        health:
            show-details: ALWAYS
logging:
    file: ./logs/admin-client.log  #添加开启admin的日志监控
```
springboot-admin-client2和springboot-admin-client一样，只需要更改端口号，这里只是看下多个服务的效果

分别启动springboot-admin-server和两个client

输入：localhost:8000


![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200910110434146.png)

输入账户admin 密码admin,也就是上面在yml文件中配置的username和password

打开Wallboard

![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200910111009543.png)

打开springboot-admin-client

![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200910111241217.png)
堆,栈,线程信息,以及内存使用情况
![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200910111417661.png)

这里我们着重看下日志
![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200910111637098.png)
![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200910111956179.png)
到这里我们就看到了实时日志；