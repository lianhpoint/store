package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest//表示当前类是一个测试类，不会随同项目一起打包
/**
 * @RunWith表示启动这个单元测试类，(单元测试类是不能运行的，项目创建时自带的不用写这个类，自己写的测试类需要写这个注解)
 * 需要传递一个参数，这个参数必须是SpringRunner.class
 */
@RunWith(SpringRunner.class)
public class UserMapperTest {
    //爆红的原因：不是因为代码错误，而是因为idea有代码检测功能，接口是不能直接创建bean的（里面有用到动态代理技术）
    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试方法的特点：就可以单独运行，不用启动整个项目，就可以做单元测试
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("tim");
        System.out.println(user);
    }

    @Test
    public void  updatePasswordByUid(){
        userMapper.updatePasswordByUid(8,"321","管理员",new Date());
    };

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(8));
    };

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(9);
        user.setPhone("99999999");
        user.setGender(1);
        user.setEmail("9999999@qq.com");
        userMapper.updateInfoByUid(user);
    }
}
