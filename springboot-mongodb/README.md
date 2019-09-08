**win安装及使用**

<a href="https://github.com/haoxiaoyong1014/springboot-examples/tree/master/springboot-mongodb/src/main/resources/static/mongodb安装及入门v1.1.pdf">mongodb安装及入门</a>

可能会因为网速的原因,如果pdf文件显示不出来,请点击下载按钮

![image.png](https://upload-images.jianshu.io/upload_images/15181329-3bb5488820b20b1a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

**linux 安装**
下载: https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-3.4.9.tgz

```yml
tar -xvzf mongodb-linux-x86_64-3.2.10.tgz     //解压
mv mongodb-linux-x86_64-3.2.10 /usr/local/mongodb      //将解压后的文件移动到指定目录并改名
cd /usr/local/mongodb/    //切换到mongodb
```

在mongodb目录下创建目录data/db ，以及/log目录

```yml
cd /usr/local/mongodb/    //切换到mongodb
mkdir data  //创建data目录
mkdir log    //创建log日志目录
cd data       //切换到data目录
mkdir db     //创建db 目录
```

系统profile配置，配置环境，这是每装一个软件的必备步骤，在profile文件最后面添加环境变量
```yml

vi /etc/profile  
  
export MONGODB_HOME=/usr/local/mongodb  
export PATH=$PATH:$MONGODB_HOME/bin 
```

保存后，重启系统配置

```yml
source /etc/profile
```

在mongodb目录下创建conf目录，并创建mongodb.conf配置文件
```yml
vim mongodb.conf

        cd /usr/local/mongodb/    //切换到mongodb
        mkdir conf //创建conf目录
        cd conf  //切换到conf  
        touch mongodb.conf  //创建mongodb.conf配置文件
```

配置一些信息在mongodb.conf 中：

```yml

dbpath = /usr/local/mongodb/data/db #数据文件存放目录  
logpath = /usr/local/mongodb/log/mongodb.log #日志文件存放目录  
port = 27017  #端口  
fork = true  #以守护程序的方式启用，即在后台运行
```

一些准备好，启动服务
```yml

cd /usr/local/mongodb/    //切换到mongodb
./bin/mongod --config ./conf/mongodb.conf  //启动服务
```

连接mongodb
```yml

cd /usr/local/mongodb/bin
./mongo
```

停止服务
```yml

 cd /usr/local/mongodb/bin
 ./mongod -shutdown -dbpath=/usr/local/mongodb/data/db  //停止mongodb
```

ps：如果远程访问数据库的话，可能是一下原因：mongodb的配置文件中的bind_ip 默认为127.0.0.1，默认只有本机可以连接。 此时，需要将bind_ip配置为0.0.0.0，表示接受任何IP的连接。
```yml

dbpath = /usr/local/mongodb/data/db #数据文件存放目录  
logpath = /usr/local/mongodb/log/mongodb.log #日志文件存放目录  
port = 27017  #端口  
fork = true  #以守护程序的方式启用，即在后台运行
bind_ip=0.0.0.0
```
