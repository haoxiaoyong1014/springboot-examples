package cn.haoxy.mybatis.plus.page.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MyPage<T> extends Page<T> {

    public Integer selectInt;
    public String selectStr;


    public MyPage(long current, long size) {
        super(current, size);
    }
}
