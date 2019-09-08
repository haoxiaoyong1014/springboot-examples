package cn.haoxy.strategy.aop.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by haoxiaoyong on 2019/9/7 下午 10:48
 * e-mail: hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * Blog: www.haoxiaoyong.cn
 */
@Data
public class AnalysisMessage {

    private Long id;

    private String title;

    private String content;

    private Date create_time;

    private Long user_id;

}
