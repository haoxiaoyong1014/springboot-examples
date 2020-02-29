package cn.haoxy.mybatis.plus.condition.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long roleId;
}
