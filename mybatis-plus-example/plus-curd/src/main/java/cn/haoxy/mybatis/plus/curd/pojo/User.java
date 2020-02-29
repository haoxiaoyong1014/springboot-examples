package cn.haoxy.mybatis.plus.curd.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    public Long id;
    public String name;
    public Integer age;
    public String email;
}
