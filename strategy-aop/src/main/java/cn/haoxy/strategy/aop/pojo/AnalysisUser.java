package cn.haoxy.strategy.aop.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:40
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Data
public class AnalysisUser {

    private Long id;

    private String username;

    private String password;

    private String phone;

    private Date create_time;

    private Date update_time;

    private Byte status;

    private Integer version;

    private String realname;

}
