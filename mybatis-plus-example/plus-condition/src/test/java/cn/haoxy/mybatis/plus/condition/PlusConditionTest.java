package cn.haoxy.mybatis.plus.condition;

import cn.haoxy.mybatis.plus.condition.mapper.RoleMapper;
import cn.haoxy.mybatis.plus.condition.mapper.UserMapper;
import cn.haoxy.mybatis.plus.condition.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.function.Function;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlusConditionTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void test() {
       /* System.out.println("--------普通查询--------");
         getUsers();*/

      /*  System.out.println("----- 带子查询(sql注入) -----");
        childSql();*/

        /*System.out.println("----- 带嵌套查询 -----");
        nestingSelect();*/
        System.out.println("----- 自定义(sql注入) -----");
        List<User> users = userMapper.selectList(new QueryWrapper<User>().apply("role_id=2"));
        print(users);

    }

    private void nestingSelect() {
        List<User> users = userMapper.selectList(new QueryWrapper<User>().nested(i -> i.eq("role_id", 2L).or().eq("role_id", 3L))
                .and(i -> i.eq("age", 20)));
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().lambda().nested(i -> i.eq(User::getRoleId, 2L).or().eq(User::getRoleId, 3L))
                .and(i -> i.eq(User::getAge, 20)));
        Assert.assertEquals(users.size(), users1.size());
        print(users);
    }

    private void childSql() {
        List<User> role_id1 = userMapper.selectList(new QueryWrapper<User>().inSql("role_id", "select id from role where id =2"));
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().lambda().inSql(User::getRoleId, "select id from role where id =2"));
        Assert.assertEquals(role_id1.size(), users1.size());
        print(users1);
    }

    private void getUsers() {

        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getRoleId, 2L));
        List<User> role_id = userMapper.selectList(new QueryWrapper<User>().eq("role_id", 2L));
        Assert.assertEquals(users.size(), role_id.size());
        print(users);
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
