### Docker 部署 SpringBoot 项目整合 Redis 镜像做访问计数Demo

首先看下最终的效果: 

在浏览器输入: http://www.haoxiaoyong.cn:8181

大概就几个步骤
```
1.安装 Docker 

2.运行 Redis 镜像

3.项目准备

4.编写 Dockerfile

5.发布项目

6.测试服务

```

这里假设你已经安装了docker

####二、运行 Redis 镜像

 **1.运行镜像**

`$ docker run --name redis-6379 -p 6379:6379 -d redis`

```
Unable to find image 'redis:latest' locally
latest: Pulling from library/redis
c4bb02b17bb4: Pull complete 
58638acf67c5: Pull complete 
f98d108cc38b: Pull complete 
83be14fccb07: Pull complete 
5d5f41793421: Pull complete 
ed89ff0d9eb2: Pull complete 
Digest: sha256:0e773022cd6572a5153e5013afced0f7191652d3cdf9b1c6785eb13f6b2974b1
Status: Downloaded newer image for redis:latest
2f1f20f672e386a61644e1c08232ea34bdfd6a0c244b55fa833fcfd6dd207288
```
**2.检查镜像**
    
 * 2.1查看镜像
    
```
root@haoxy:~# docker images redis
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
redis               latest              1e70071f4af4        4 weeks ago         107MB
```
* 2.2 查看镜像进程
```
root@haoxy:~# docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
2f1f20f672e3        redis               "docker-entrypoint.s…"   14 seconds ago      Up 14 seconds       0.0.0.0:6379->6379/tcp   redis-6379
``` 
* 2.3查看容器进程 
```
root@haoxy:~# docker container ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
2f1f20f672e3        redis               "docker-entrypoint.s…"   7 minutes ago       Up 7 minutes        0.0.0.0:6379->6379/tcp   redis-6379
```   
**3.测试Redis服务**

通过 redis-cli 连接Redis 服务，测试存储数据

```
root@haoxy:~# docker run -it --link redis-6379:redis --rm redis redis-cli -h redis -p 6379
redis:6379> set count 1
OK
redis:6379> get count
"1"
redis:6379> exit
root@haoxy:~#
```
#### 三、项目准备

**1.编译项目**

* 1.自己在服务器Maven编译项目

使用 git 克隆项目

> $ git clone https://github.com/haoxiaoyong1014/springboot-examples
    
使用 maven 编译项目

`cd springboot-examples/springboot-redis-docker/`

**2.修改项目**

1.修改 Redis 服务器地址spring.redis.host=116.62.187.190,为本地IP, 
在运行 Redis 镜像时候 已经把 Redis 做成外网服务了0.0.0.0:6379->6379/tcp
```
$ vi src/main/resources/application.properties
```
```
#Redis服务器地址
spring.redis.host=116.62.187.190
```
```
$ mvn package
```
复制target/ 目录下的docker-spring-boot-demo-0.0.1-SNAPSHOT.jar项目到/opt 目录稍后会用到
```
$ cp target/docker-spring-boot-demo-0.0.1-SNAPSHOT.jar /opt/
```
2.自己在本地Maven编译项目，然后上传到/opt 目录稍后会用到

修改application.properties 的 Redis 服务器地址

```
springboot-examples/springboot-redis-docker/src/main/resources/application.properties
```

```
# Redis服务器地址
spring.redis.host=116.62.187.190
```
####四、编写 Dockerfile

编写 Dockerfile 基于java:8镜像为基础
```
$ cd /opt/
$ touch Dockerfile
$ vi Dockerfile
```
编辑内容个如下
```
# 基于哪个镜像
FROM java:8

# 将本地文件夹挂载到当前容器
VOLUME /tmp

# 拷贝文件到容器，也可以直接写成ADD docker-spring-boot-demo-0.0.1-SNAPSHOT.jar /souyunku-app.jar
ADD docker-spring-boot-demo-0.0.1-SNAPSHOT.jar souyunku-app.jar
RUN bash -c 'touch /souyunku-app.jar'

# 开放8181端口
EXPOSE 8181

# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/souyunku-app.jar"]
```
#### 五、发布项目

* 1.编译镜像
```
$ cd /opt/
$ docker build -t souyunku-app:v1 .
```
看到如下信息，就证明你的Dockerfile写的没毛病，而且镜像也编译成功了
```
Sending build context to Docker daemon  18.72MB
Step 1/6 : FROM java:8
8: Pulling from library/java
5040bd298390: Pull complete 
fce5728aad85: Pull complete 
76610ec20bf5: Pull complete 
60170fec2151: Pull complete 
e98f73de8f0d: Pull complete 
11f7af24ed9c: Pull complete 
49e2d6393f32: Pull complete 
bb9cdec9c7f3: Pull complete 
Digest: sha256:c1ff613e8ba25833d2e1940da0940c3824f03f802c449f3d1815a66b7f8c0e9d
Status: Downloaded newer image for java:8
 ---> d23bdf5b1b1b
Step 2/6 : VOLUME /tmp
 ---> Running in 0559a62b0cd5
Removing intermediate container 0559a62b0cd5
 ---> b1f3846913a4
Step 3/6 : ADD docker-spring-boot-demo-0.0.1-SNAPSHOT.jar souyunku-app.jar
 ---> 9f60dad5d2ac
Step 4/6 : RUN bash -c 'touch /souyunku-app.jar'
 ---> Running in 39d5c09ab614
Removing intermediate container 39d5c09ab614
 ---> 2b691adf7922
Step 5/6 : EXPOSE 80
 ---> Running in 11a577437a23
Removing intermediate container 11a577437a23
 ---> 78815d6fe6b2
Step 6/6 : ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/souyunku-app.jar"]
 ---> Running in eca10fed3d02
Removing intermediate container eca10fed3d02
 ---> 8ec4e85a0f05
Successfully built 8ec4e85a0f05
Successfully tagged souyunku-app:v1
```
* 2.查看镜像
```
root@haoxy:/opt# docker images souyunku-app
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
souyunku-app        v1                  8ec4e85a0f05        2 minutes ago       681MB
```
* 3.运行镜像

后台守护进程运行，然后把容器端口映射到，外网端口8181
```
root@haoxy:/opt# docker run --name MySpringBoot -d -p 8181:8181 souyunku-app:v1
e68d438603619e363883d4eae65d3918e1c3e00f867731207bccf06f5690dc64
```
* 4.查看进程

查看容器进程，可以看到redis 在 6379端口，MySpringBoot 项目在 8181端口

```
root@haoxy:/opt# docker container ps
CONTAINER ID        IMAGE               COMMAND                  CREATED              STATUS              PORTS                    NAMES
e68d43860361        souyunku-app:v1     "java -Djava.securit…"   About a minute ago   Up About a minute   0.0.0.0:80->80/tcp       MySpringBoot
0f9646171edd        redis               "docker-entrypoint.s…"   39 minutes ago       Up 39 minutes       0.0.0.0:6379->6379/tcp   redis-6379
```
#### 六、测试服务

浏览器访问：http://127.0.0.1:8181,当然我没有输入127.0.0.1 我是在服务器上直接做的，用的公网IP

![image](https://github.com/haoxiaoyong1014/springboot-examples/raw/master/springboot-redis-docker/src/main/resources/static/images/redis-demo.gif)

### Docker Compose

Docker Compose 是 Docker 官方编排（Orchestration）项目之一，负责快速在集群中部署分布式应用。

一个使用Docker容器的应用，通常由多个容器组成。使用Docker Compose，不再需要使用shell脚本来启动容器。在配置文件中，
所有的容器通过services来定义，然后使用docker-compose脚本来启动，停止和重启应用，和应用中的服务以及所有依赖服务的容器

可以看下这篇文章: https://blog.csdn.net/haoxiaoyong1014/article/details/80104588