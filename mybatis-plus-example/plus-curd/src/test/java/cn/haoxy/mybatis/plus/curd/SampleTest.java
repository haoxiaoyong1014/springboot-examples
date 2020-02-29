package cn.haoxy.mybatis.plus.curd;

import cn.haoxy.mybatis.plus.curd.mapper.UserMapper;
import cn.haoxy.mybatis.plus.curd.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        System.out.println("--------insert method test--------");
        User user = new User();
        user.setAge(23);
        user.setEmail("test3.@123.com");
        user.setName("王3");
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testDel() {
        Assert.assertTrue(userMapper.deleteById(3L) > 0);
        Assert.assertTrue(userMapper.delete(new QueryWrapper<User>().lambda().eq(User::getName, "Sandy")) > 0);
    }

    @Test
    public void testUpdate() {
        Assert.assertTrue(userMapper.updateById(new User().setId(1L).setEmail("ab@c.c")) > 0);
        userMapper.update(new User().setName("haox").setAge(3), new UpdateWrapper<User>().lambda().eq(User::getId, 2));
    }

    @Test
    public void dSelect() {
        Assert.assertEquals("ab@c.c", userMapper.selectById(1L).getEmail());
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getId, 2));
        Assert.assertEquals("haox", user.getName());
    }

    @Test
    public void orderBy() {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.orderByAsc("age");
        System.out.println(userMapper.selectList(qw));
    }
    @Test
    public void orderByLambda() {
        LambdaQueryWrapper<User> lw = new LambdaQueryWrapper<>();
        lw.orderByAsc(User::getAge);
        System.out.println(userMapper.selectList(lw));
    }
}
