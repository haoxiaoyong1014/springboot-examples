package cn.haoxy.mongodb.request;

import lombok.Data;

/**
 * Created by Haoxy on 2019-05-09.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 */

@Data
public class QueryPageRequest {

    //站点id
    private String siteId;
    //页面 Id
    private String pageId;
    //页面名称
    private String pageName;
    //别名
    private String pageAilase;
    //模板 id
    private String templateId;

}
