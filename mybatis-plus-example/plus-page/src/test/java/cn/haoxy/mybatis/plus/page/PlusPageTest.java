package cn.haoxy.mybatis.plus.page;

import cn.haoxy.mybatis.plus.page.mapper.UserMapper;
import cn.haoxy.mybatis.plus.page.pojo.MyPage;
import cn.haoxy.mybatis.plus.page.pojo.ParamSome;
import cn.haoxy.mybatis.plus.page.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PlusPageTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void pageTest() {
        /*System.out.println("----- baseMapper 自带分页 -----");
        iPage();*/
        /*System.out.println("----- 自定义 XML 分页 -----");
        MyPage();*/
        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(20).setSelectStr("Jack");
        ParamSome paramSome = new ParamSome(20, "test2@baomidou.com");
        MyPage<User> userMyPage = userMapper.mySelectPageAndPs(myPage,paramSome);
        System.out.println("总条数 -----> " + userMyPage.getTotal());
        System.out.println("当前页数 -----> " + userMyPage.getCurrent());
        System.out.println("当前每页显示数 -----> " + userMyPage.getSize());
        print(userMyPage.getRecords());

    }

    private void MyPage() {

        MyPage<User> myPage = new MyPage<User>(1, 5).setSelectInt(20).setSelectStr("Jack");
        MyPage<User> userMyPage = userMapper.mySelectPage(myPage);
        System.out.println("总条数 -----> " + userMyPage.getTotal());
        System.out.println("当前页数 -----> " + userMyPage.getCurrent());
        System.out.println("当前每页显示数 -----> " + userMyPage.getSize());
        print(userMyPage.getRecords());
    }

    private void iPage() {
        Page<User> page = new Page<>(1, 5);
        IPage<User> userIPage = userMapper.selectPage(page, new QueryWrapper<User>().lambda().eq(User::getAge, 20).eq(User::getName, "Jack"));
        System.out.println("总条数------>" + userIPage.getTotal());
        System.out.println("当前页数 -----> " + userIPage.getCurrent());
        System.out.println("当前每页显示数 -----> " + userIPage.getSize());

        /*import ikidou.reflect.TypeBuilder;
        System.out.println("json 正反序列化 begin");
        String json = JSON.toJSONString(page);
        Page<User> page1 = JSON.parseObject(json, TypeBuilder.newInstance(Page.class).addTypeParam(User.class).build());
        print(page1.getRecords());
        System.out.println("json 正反序列化 end");*/

        System.out.println("-------------------");
        System.out.println("总条数------>" + page.getTotal());
        System.out.println("当前页数 -----> " + page.getCurrent());
        System.out.println("当前每页显示数 -----> " + page.getSize());
        print(userIPage.getRecords());
    }

    private <T> void print(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(System.out::println);
        }
    }
}
