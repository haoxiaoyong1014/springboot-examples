package cn.haoxy.strategy.aop.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:54
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Data
public class AnalysisRole {

    private Long id;

    private String name;

    private String code;

    private Date create_time;

    private Date update_time;

    private String descpt;

    private Byte status;

}
