### Spring,Spring Boot 实战,- 全局异常处理

#### 异常处理
当我们开发过程中或者说一个web程序在运行中,由于用户操作不当,或者说程序中存在的bug,有大量的异常需要处理,有些异常要告知开发人员,有些异常要提示用户,

在很久之前我们处理全局异常通常都定义一个异常基类,然后每个Controller去继承这个异常基类,虽然这种方式可以解决问题，但是极其不灵活，因为动用了继承机制就只为获取一个默认的方法，这显然是不好的。

在探寻spring的异常处理机制的时候发现@ExceptionHandler注解来处理异常,下面就介绍一下这种方式:

**1,自定义一个异常类并继承RuntimeException**

```java
public class CustomizeException extends RuntimeException {


    private String content;


    public CustomizeException() {
    }

    public CustomizeException(int code, String msg) {
        JSONObject returnJson = new JSONObject();
        returnJson.put("code", code);
        returnJson.put("msg", msg);
        this.setContent(returnJson.toJSONString());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

```
**2,定义一个全局的异常处理类**

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理系统异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String systemErrorHandler(Exception ex) {
        RespInfo respInfo = new RespInfo();
        respInfo.setCode(400);
        respInfo.setMsg("系统异常");
        respInfo.setData(ex.getMessage());
        return JSONObject.toJSONString(respInfo);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = CustomizeException.class)
    @ResponseBody
    public String customizeException(CustomizeException ce) {
       return ce.getContent();
    }
}
```

所谓加强Controller就是`@ControllerAdvice`注解,这中处理异常的方式也叫`使用加强Controller做全局异常处理。`

`@ExceptionHandler`中的value值就是需要异常类(自定义异常(CustomizeException),系统异常(Exception,NullPointerException,ArrayIndexOutOfBoundsException等等..))

**定义一个RespInfo**

用于接收系统异常参数

```java
public class RespInfo {

    public int code;

    public String msg;

    public Object data;
    
    //省略 get和 set 方法
}
```
**定义一个Controller**

```java
@RestController
@RequestMapping(value = "login")
public class LoginController {

    @RequestMapping(value = "in")
    public String login(@RequestBody Parameter parameter) {

        if (StringUtils.isAnyBlank(parameter.getPassword(), parameter.getPhone())) {
            throw new CustomizeException(1001, "必要参数不能为空");
        }
        //int i = 1 / 0;

        if (!"1111".equals(parameter.getPhone()) || !"1234".equals(parameter.getPassword())) {
            throw new CustomizeException(1002, "参数错误");
        }
        return "登录成功";
    }
}
```
其中Parameter是要用户传过来的参数; 

StringUtils.isAnyBlank(CharSequence... css)依赖于:

```xml
 <dependency>
   <groupId>org.apache.commons</groupId>
   <artifactId>commons-lang3</artifactId>
   <version>3.7</version>
 </dependency>
```

任意一个值为空都返回true,

**接下来我们进行测试**

当有空参的时候:

![image.png](https://upload-images.jianshu.io/upload_images/15181329-8aaaa9fa6c5ca17a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

当参数值不正确时:

![image.png](https://upload-images.jianshu.io/upload_images/15181329-3081541ff8ebac78.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

当发生代码有异常时:

我们把 `int i = 1 / 0` 注解打开,

![image.png](https://upload-images.jianshu.io/upload_images/15181329-92bff2d275a3a94c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



