### ELK安装时错误记录

#### 错误1：`error='Cannot allocate memory' (errno=12)`

**解决方法:**

由于`elasticsearch`默认分配`jvm`空间大小为`2g`，需要改小一点

```shell
vim config/jvm.options  
-Xms2g  →  -Xms512m
-Xmx2g  →  -Xmx512m
```

#### 错误2：`can not run elasticsearch as root`

**解决方法：**

在 Linux 环境中，elasticsearch 不允许以 root 权限来运行！所以需要创建一个非root用户，以非root用户来起es

```sh
#这里创建的用户名为haoxy,可以随意
useradd haoxy
#为新创建的用户设置密码
passwd haoxy
#将安装权限归新用户所有
chown -R haoxy:haoxy /usr/local/elk/elasticsearch-7.7.0
```

#### 错误3：`错误: 找不到或无法加载主类 org.elasticsearch.tools.java_version_checker.JavaVersionChecker`

**解决方法：**

我出现这个问题的原因是因为：我是使用root用户登录的开发机，ES默认安装在了root目录下。使用su命令切换用户后，执行`./elasticsearch`时找不到启动类导致。 解决方案：使用`elastic`用户，将`<ElasticSearch>`安装包copy到`elastic`用户的`home`目录下，然后去`home`目录下执行启动操作

```sh
cp <ElasticSearch>/ ~/home/elasticsearch
cd ~/home/elasticsearch
./bin/elasticsearch
```

#### 错误4：`max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]`

一开始我是用我的1核2G的阿里云服务器搭建的ES,出现这个问题的原因是当前服务器的内存不够用；(因为我的阿里云服务器上跑这其他的程序)果断在自己的机子上搭建一个虚拟机给了4G的运行内存；