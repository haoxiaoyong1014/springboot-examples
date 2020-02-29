package cn.haoxy.mybatis.plus.page.mapper;

import cn.haoxy.mybatis.plus.page.pojo.MyPage;
import cn.haoxy.mybatis.plus.page.pojo.ParamSome;
import cn.haoxy.mybatis.plus.page.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<User> {

    MyPage<User> mySelectPageAndPs(@Param("pg") MyPage<User> myPage,@Param("ps") ParamSome paramSome);//

    MyPage<User> mySelectPage(@Param("pg") MyPage<User> myPage);
}
