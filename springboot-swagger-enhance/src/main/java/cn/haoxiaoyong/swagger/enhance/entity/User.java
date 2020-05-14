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
