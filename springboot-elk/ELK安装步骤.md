### ELK安装步骤

#### 简单介绍


- ElasticSearch:用于存储日志信息。
- Logstash:用于收集、处理和转发日志信息。
- Kibana:提供可搜索的Web可视化界面。

#### 准备工作安装JDK

- Elasticsearch7 自带jdk11，如果没有安装jdk, es7使用缺省jdk11
- 如果已安装，使用已安装的jdk，低于11有警告，但不影响使用。
- 但是在安装Logstash还是需要java环境，所以建议还是安装一下jdk11

* 安装jdk过程省略

#### 安装Elasticsearch

```shell
#下载elasticsearch安装包
wget https://mirrors.huaweicloud.com/elasticsearch/7.7.0/elasticsearch-7.7.0-linux-x86_64.tar.gz
#解压
tar -xvf elasticsearch-7.7.0-linux-x86_64.tar.gz 
```

为Elasticsearch创建专属用户（Elasticsearch要求不能以root账户来运行）

```powershell
#这里创建的用户名为haoxy
useradd haoxy
#为新创建的用户设置密码
passwd haoxy
#将安装权限归新用户所有
chown -R haoxy:haoxy /usr/local/elk/elasticsearch-7.7.0
#为elasticsearch运行准备修改系统配置文件
echo 'vm.max_map_count=262144' >> /etc/sysctl.conf
echo 'haoxy hard nofile 65536' >> /etc/security/limits.conf
echo 'haoxy soft nofile 65536' >> /etc/security/limits.conf
```

修改配置文件

```shell
#进入elasticsearch文件目录
cd elasticsearch-7.7.0/config
vi elasticsearch.yml
#打开这些配置的注释 ，然后填上对应的值
network.host: 你自己的服务器ip
http.port: 9200
discovery.seed_hosts: ["ip地址"]
#打开这个配置项
node-name
#node-1这个值是node-name配置的值，默认就是node-1
cluster.initial_master_nodes: ["node-1"]
```

启动Easticsearch

```shell
#切换角色
[root@localhost elasticsearch-7.7.0]# su suyu
#启动,建议第一次启动的时候先不要使用 -d 参数，因为第一次启动很有可能会有报错，如果你很自信就可以加上-d 
[haoxy@localhost elasticsearch-7.7.0]$ bin/elasticsearch -d
```

启动只有输出日志大致是这个样子：

```verilog
a43f33) Copyright (c) 2020 Elasticsearch BV
[2020-09-04T15:16:13,769][INFO ][o.e.d.DiscoveryModule    ] [node-1] using discovery type [zen] and seed hosts providers [settings]
[2020-09-04T15:16:15,269][INFO ][o.e.n.Node               ] [node-1] initialized
[2020-09-04T15:16:15,270][INFO ][o.e.n.Node               ] [node-1] starting ...
[2020-09-04T15:16:15,484][INFO ][o.e.t.TransportService   ] [node-1] publish_address {10.1.56.75:9300}, bound_addresses {10.1.56.75:9300}
[2020-09-04T15:16:15,911][INFO ][o.e.b.BootstrapChecks    ] [node-1] bound or publishing to a non-loopback address, enforcing bootstrap checks
[2020-09-04T15:16:15,944][INFO ][o.e.c.c.Coordinator      ] [node-1] cluster UUID [aY5lgyvbRuqb61LQ8A6hKA]
[2020-09-04T15:16:16,304][INFO ][o.e.c.s.MasterService    ] [node-1] elected-as-master ([1] nodes joined)[{node-1}{ALYnSqgTTM2yRFGOvHhA_A}{zyCxSJGsS8y2ODCmZHJQNA}{10.1.56.75}{10.1.56.75:9300}{dilmrt}{ml.machine_memory=3974909952, xpack.installed=true, transform.node=true, ml.max_open_jobs=20} elect leader, _BECOME_MASTER_TASK_, _FINISH_ELECTION_], term: 2, version: 28, delta: master node changed {previous [], current [{node-1}{ALYnSqgTTM2yRFGOvHhA_A}{zyCxSJGsS8y2ODCmZHJQNA}{10.1.56.75}{10.1.56.75:9300}{dilmrt}{ml.machine_memory=3974909952, xpack.installed=true, transform.node=true, ml.max_open_jobs=20}]}
[2020-09-04T15:16:16,432][INFO ][o.e.c.s.ClusterApplierService] [node-1] master node changed {previous [], current [{node-1}{ALYnSqgTTM2yRFGOvHhA_A}{zyCxSJGsS8y2ODCmZHJQNA}{10.1.56.75}{10.1.56.75:9300}{dilmrt}{ml.machine_memory=3974909952, xpack.installed=true, transform.node=true, ml.max_open_jobs=20}]}, term: 2, version: 28, reason: Publication{term=2, version=28}
[2020-09-04T15:16:16,539][INFO ][o.e.h.AbstractHttpServerTransport] [node-1] publish_address {10.1.56.75:9200}, bound_addresses {10.1.56.75:9200}
[2020-09-04T15:16:16,540][INFO ][o.e.n.Node               ] [node-1] started
[2020-09-04T15:16:16,860][INFO ][o.e.l.LicenseService     ] [node-1] license [091daa59-1347-45db-8b02-b3e8b570315b] mode [basic] - valid
[2020-09-04T15:16:16,862][INFO ][o.e.x.s.s.SecurityStatusChangeListener] [node-1] Active license is now [BASIC]; Security is disabled
[2020-09-04T15:16:16,877][INFO ][o.e.g.GatewayService     ] [node-1] recovered [0] indices into cluster_state
```

然后在浏览器键入 ip:9200

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/img001.png" alt="image-20200904162234030" style="zoom:50%;"/>

至此Easticsearch就安装完成了；

#### 安装Logstash

```shell
#下载logstash安装包
wget https://mirrors.huaweicloud.com/logstash/7.7.0/logstash-7.7.0.tar.gz
#解压logstash
tar -xvf logstash-7.7.0.tar.gz 
#进入logstash配置文件夹下
cd logstash-7.7.0/config/
#文件中加入下面这段内
vim logstash.conf
##################################################
input {
  tcp {
    mode => "server"
    host => "10.1.56.75"    					#安装logstash的ip
    port => 4560                 				#设置logstash的端口
    codec => json_lines
  }
}
output {
  elasticsearch {
    hosts => ["10.1.56.75:9200"]    			#es的ip和端口（本次logstash和es是同一个服务器）
    index => "java-logstash-%{+YYYY.MM.dd}"     #es的index名称
    #user => "haoxy"							#es的账号
    #password => "haoxy"						#es的密码
  }
}
##################################################
#进入logstash-7.7.0/目录下启动，启动成功之后 win:crtl+c,mac:control+c 即可
bin/logstash -f config/logstash.conf &
```

#### 安装Kibana

```shell
#切换到root角色下载kibana包
wget https://mirrors.huaweicloud.com/kibana/7.7.0/kibana-7.7.0-linux-x86_64.tar.gz
#解压
tar -xvf kibana-7.7.0-linux-x86_64.tar.gz 
#让这个文件夹可写
chmod 777 kibana-7.7.0-linux-x86_64
#将目录权限归我们创建新用户所有，这里也不能以root角色启动
chown -R haoxy:haoxy /usr/local/elk/kibana-7.7.0-linux-x86_64
#进入kibana目录
cd kibana-7.7.0-linux-x86_64
#修改配置文件
vim ./config/kibana.yml

#将这行配置打开，将值配置为elasticsearch服务器的ip地址
elasticsearch.hosts: ["http://10.1.56.75:9200"]
#打开这行配置，默认为localhost，改为0.0.0.0不改外网访问不了
server.host: "0.0.0.0"
#进入bin目录启动
cd bin/
#切换角色启动，这里也不准用root启动
su haoxy
#以后台形式启动 默认占用端口2601
./kibana &
```

启动成功日志大致如下：

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/img002.png" style="zoom:50%;" />

到此我们的ELK就搭建完成了，登录到kibana可视化界面 http://10.1.56.75:5601

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/img003.png" style="zoom:50%;" />

#### springboot集成ELK系统

然后启动springboot项目，在pom文件中依赖logstash插件，将日志文件发送到logstash中，logstash会将日志文件传到elasticsearch中，kibana拉取elasticsearch中的日志，我们就能看到日志啦

```xml
 <dependency>
      <groupId>net.logstash.logback</groupId>
      <artifactId>logstash-logback-encoder</artifactId>
      <version>5.3</version>
  </dependency>
```

在resourcet目录中添加日志的配置文件`logback-spring.xml`，修改`destination`为你自己的logstash的ip和端口就行

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--该日志将日志级别不同的log信息保存到不同的文件中 -->
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml"/>

    <springProperty scope="context" name="springAppName"
                    source="spring.application.name"/>
    <springProperty scope="context" name="serverPort"
                    source="server.port"/>

    <!-- 日志在工程中的输出位置 -->
    <property name="LOG_FILE" value="${BUILD_FOLDER:-build}/${springAppName}"/>

    <!-- 控制台的日志输出样式 -->
    <property name="CONSOLE_LOG_PATTERN"
              value="%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}}"/>

    <!-- 控制台输出 -->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>INFO</level>
        </filter>
        <!-- 日志输出编码 -->
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>utf8</charset>
        </encoder>
    </appender>

    <!-- 为logstash输出的JSON格式的Appender -->
    <appender name="logstash"
              class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>10.1.56.75:4560</destination>
        <!-- 日志输出编码 -->
        <encoder
                class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp>
                    <timeZone>UTC</timeZone>
                </timestamp>
                <pattern>
                    <pattern>
                        {
                        "severity": "%level",
                        "service": "${springAppName:-}",
                        "port": "${serverPort:-}",
                        "trace": "%X{X-B3-TraceId:-}",
                        "span": "%X{X-B3-SpanId:-}",
                        "exportable": "%X{X-Span-Export:-}",
                        "pid": "${PID:-}",
                        "thread": "%thread",
                        "class": "%logger{40}",
                        "rest": "%message"
                        }
                    </pattern>
                </pattern>
            </providers>
        </encoder>
    </appender>

    <!-- 日志输出级别 -->
    <root level="INFO">
        <appender-ref ref="console"/>
        <appender-ref ref="logstash"/>
    </root>
</configuration>
```

编写一个Controller

```java
@RestController
@Slf4j
public class ElkController {

    @RequestMapping("elk")
    public String testElk(String params) {

        log.info("接口入参 {}", params);

        params = "Hello World";

        log.error("error message {}", params);

        return params;
    }
}
```

我们访问一下这个ElkController并让日志打印到控制台：

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/img004.png" style="zoom:50%;" />

这时我们去`kibana`配置索引信息，创建搜索规则就能将日志显示到`Discover`啦！

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/img005.png" style="zoom:50%;" />

创建索引信息，这里的索引是我们当初配置logstash配置文件时候写好的

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200905145710709.png" alt="image-20200905145710709" style="zoom:50%;" />

点击create

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200905150007090.png" style="zoom:50%;" />

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200905150230663.png" alt="image-20200905150230663" style="zoom:50%;" />

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/image-20200905150331417.png" alt="image-20200905150331417" style="zoom:50%;" />

<img src="http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/001.png" style="zoom:50%;" />

到此我们ELK日志分析系统就已经搭建完成了，springboot日志也发到了日志系统中了；