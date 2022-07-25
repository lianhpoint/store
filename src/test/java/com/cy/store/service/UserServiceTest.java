package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.service.ex.UserNotFoundException;
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
public class UserServiceTest {
    //爆红的原因：不是因为代码错误，而是因为idea有代码检测功能，接口是不能直接创建bean的（里面有用到动态代理技术）
    @Autowired
    private IUserService userService;


    /**
     * 单元测试方法的特点：就可以单独运行，不用启动整个项目，就可以做单元测试
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */
    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("test02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
            //获取类的对象，在获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("test01", "123");
        System.out.println(user);
    }

    @Test
    public void login02() {
        try {
            String username = "test02";
            String password = "123";
            User user = userService.login(username, password);
            System.out.println("登录成功！" + user);
        } catch (ServiceException e) {
            System.out.println("登录失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changePassword(){
        userService.changePassword(9,"管理员","123","321");
    }

    @Test
    public void getByUid() {
        System.out.println(userService.getByUid(9));
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("888888888");
        user.setEmail("88888888@qq.com");
        user.setGender(0);
        userService.changeInfo(9,"管理员",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(11,"update/test.jpg","小明");
    }

}
