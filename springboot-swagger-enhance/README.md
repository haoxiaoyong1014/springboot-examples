### springboot-swagger-enhance

此 demo 主要演示如何集成第三方的 swagger 来替换原生的 swagger，美化文档样式。本 demo 使用[swagger-bootstrap-ui](https://github.com/xiaoymin/swagger-bootstrap-ui)
启动项目，访问地址 http://${host}:${port}/doc.html

#### UI增强
同时，swagger-bootstrap-ui在满足以上功能的同时，还提供了文档的增强功能，这些功能是官方swagger-ui所没有的，每一个增强的功能都是贴合实际,考虑到开发者的实际开发需要,是比不可少的功能，主要包括：

个性化配置：通过个性化ui配置项，可自定义UI的相关显示信息

离线文档：根据标准规范，生成的在线markdown离线文档，开发者可以进行拷贝生成markdown接口文档，通过其他第三方markdown转换工具转换成html或pdf，这样也可以放弃swagger2markdown组件

接口排序：自1.8.5后，ui支持了接口排序功能，例如一个注册功能主要包含了多个步骤,可以根据swagger-bootstrap-ui提供的接口排序规则实现接口的排序，step化接口操作，方便其他开发者进行接口对接

#### UI特点
以markdown形式展示文档,将文档的请求地址、类型、请求参数、示例、响应参数分层次依次展示,接口文档一目了然,方便开发者对接
在线调试栏除了自动解析参数外,针对必填项着颜色区分,同时支持tab键快速输入上下切换.调试时可自定义Content-Type请求头类型
个性化配置项,支持接口地址、接口description属性、UI增强等个性化配置功能
接口排序,支持分组及接口的排序功能
支持markdown文档离线文档导出,也可在线查看离线文档
调试信息全局缓存,页面刷新后依然存在,方便开发者调试
以更人性化的treetable组件展示Swagger Models功能
响应内容可全屏查看,针对响应内容很多的情况下，全屏查看，方便调试、复制
文档以多tab方式可显示多个接口文档
请求参数栏请求类型、是否必填着颜色区分
主页中粗略统计接口不同类型数量
支持接口在线搜索功能
左右菜单和内容页可自由拖动宽度
支持自定义全局参数功能，主页包括header及query两种类型
i18n国际化支持,目前支持：中文简体、中文繁体、英文
JSR-303 annotations 注解的支持

#### 使用说明

由于是springfox-swagger的增强UI包,所以基础功能依然依赖Swagger,springfox-swagger的jar包必须引入
```xml
<dependency>
  <groupId>io.springfox</groupId>
  <artifactId>springfox-swagger2</artifactId>
  <version>2.9.2</version>
</dependency>
```

然后引入SwaggerBootstrapUi的jar包
```xml
<dependency>
  <groupId>com.github.xiaoymin</groupId>
  <artifactId>swagger-bootstrap-ui</artifactId>
  <version>1.9.6</version>
</dependency>
```
#### 编写Swagger2Config配置文件
```java
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

 @Bean
 public Docket createRestApi() {
     return new Docket(DocumentationType.SWAGGER_2)
     .apiInfo(apiInfo())
     .select()
     .apis(RequestHandlerSelectors.basePackage("com.bycdao.cloud"))
     .paths(PathSelectors.any())
     .build();
 }

 private ApiInfo apiInfo() {
     return new ApiInfoBuilder()
     .title("swagger-bootstrap-ui RESTful APIs")
     .description("swagger-bootstrap-ui")
     .termsOfServiceUrl("http://localhost:8999/")
     .version("1.0")
     .build();
  }
}
```
**ApiResponse.java**

```java
package cn.haoxiaoyong.swagger.enhance.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author haoxiaoyong on 2020/5/14 下午 3:55
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "通用PI接口返回", description = "Common Api Response")
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -8987146499044811408L;
    /**
     * 通用返回状态
     */
    @ApiModelProperty(value = "通用返回状态", required = true)
    private Integer code;
    /**
     * 通用返回信息
     */
    @ApiModelProperty(value = "通用返回信息", required = true)
    private String message;
    /**
     * 通用返回数据
     */
    @ApiModelProperty(value = "通用返回数据", required = true)
    private T data;
}

```

**User.java**

```java
package cn.haoxiaoyong.swagger.enhance.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author haoxiaoyong on 2020/5/14 下午 3:56
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户实体", description = "User Entity")
public class User {

    private static final long serialVersionUID = 5057954049311281252L;
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id", required = true)
    private Integer id;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String name;
    /**
     * 工作岗位
     */
    @ApiModelProperty(value = "工作岗位", required = true)
    private String job;
}

```

**UserController.java**

```java
package cn.haoxiaoyong.swagger.enhance.controller;

import cn.haoxiaoyong.swagger.enhance.common.ApiResponse;
import cn.haoxiaoyong.swagger.enhance.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author haoxiaoyong on 2020/5/14 下午 3:54
 * e-mail: hxyHelloWorld@163.com
 * github: https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@RestController
@RequestMapping("/user")
@Api(tags = "1.0.0-SNAPSHOT", description = "用户管理", value = "用户管理")
@Slf4j
public class UserController {

    @GetMapping
    @ApiOperation(value = "条件查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名",  defaultValue = "xxx")})
    public ApiResponse<User> getByUserName(String username) {
        log.info("多个参数用  @ApiImplicitParams");
        return ApiResponse.<User>builder().code(200).message("操作成功").data(new User(1, username, "JAVA")).build();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户编号",dataType = "int")})
    public ApiResponse<User> get(@PathVariable Integer id) {
        log.info("单个参数用  @ApiImplicitParam");
        return ApiResponse.<User>builder().code(200).message("操作成功").data(new User(id, "u1", "p1")).build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）", notes = "备注")
    @ApiImplicitParam(name = "id", value = "用户编号",dataType = "int")
    public void delete(@PathVariable Integer id) {
        log.info("单个参数用 ApiImplicitParam");
    }

    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    public User post(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    @PostMapping("/multipar")
    @ApiOperation(value = "添加用户（DONE）")
    public List<User> multipar(@RequestBody List<User> user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");

        return user;
    }

    @PostMapping("/array")
    @ApiOperation(value = "添加用户（DONE）")
    public User[] array(@RequestBody User[] user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }

    @PostMapping("/{id}/file")
    @ApiOperation(value = "文件上传（DONE）")
    public String file(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info(file.getContentType());
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        return file.getOriginalFilename();
    }
}

```

#### UI界面

![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/FireShot1.png)
![](http://cg-mall.oss-cn-shanghai.aliyuncs.com/blog/FireShot%20Capture%20006%20-%20swagger-bootstrap-ui%20RESTful%20APIs%20-%20localhost.png)
