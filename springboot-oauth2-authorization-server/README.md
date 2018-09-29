### springboot-oauth2

2018/6/20 添加 springboot-oauth2

* springboot-oauth2 包括: springboot-oauth2-authorization-server(认证服务)和springboot-oauth2-resource-server(资源服务)

**授权码模式:** 

* 访问认证服务器 http://localhost:8888/oauth/authorize?response_type=code&client_id=merryyou&redirect_uri=https://github.com/haoxiaoyong1014?tab=repositories&scope=all

* 是否同意并授权

![image](https://github.com/haoxiaoyong1014/springboot-examples/raw/master/springboot-rabbitmq/src/main/java/com/hxy/rabbitmq/img/v7.jpg)

* 假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码(code)。

![image](https://github.com/haoxiaoyong1014/springboot-examples/raw/master/springboot-rabbitmq/src/main/java/com/hxy/rabbitmq/img/v4.jpg)

* 拿到这个授权码(code)去交换 access_token
    * 认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。
    
![image](https://github.com/haoxiaoyong1014/springboot-examples/raw/master/springboot-rabbitmq/src/main/java/com/hxy/rabbitmq/img/v8.jpg) 
![image](https://github.com/haoxiaoyong1014/springboot-examples/raw/master/springboot-rabbitmq/src/main/java/com/hxy/rabbitmq/img/v9.jpg) 
* 还有一点需要说明 在请求头中处理了加入 Content-Type : application/x-www-form-urlencoded 还要加入:Authorization:Basic bWVycnl5b3U6bWVycnl5b3U=  
 其中 Authorization的值是 CLIENT_ID 和 CLIENT_SECRET Base64加密得到 详细内容在 springboot-examples/springboot-oauth2-authorization-server/src/test/java/cn/merryyou/security/SpringBoot2Oauth2Test.java 中
 
 ![image](https://github.com/haoxiaoyong1014/springboot-examples/raw/master/springboot-rabbitmq/src/main/java/com/hxy/rabbitmq/img/v6.jpg)    




