package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDupcalitedException;
import com.cy.store.util.JsonResult;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController//等于@Controller+@ResponseBody
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;

    /**
     控制层如何接收前端的数据?
     1.接收数据方式一：请求处理方法的参数列表设置为pojo类型来接收前端的数据，
     就是后端采用对象来接收数据
     springboot会将前端的URL地址中的参数名和pojo类的属性名进行比较，如果这两个
     名称相同，则将值注入到pojo类中对应的属性上
     */
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
       userService.reg(user);
       return new JsonResult<>(ok);
    }

    /**
     控制层如何接收前端的数据?
     2.接收数据方式一：请求处理方法的参数列表设置为非pojo类型
     就是后端采用对象来接收数据
     springboot会将请求的参数名和方法的参数名直接进行比较，如果名称相同
     则自动完成值的依赖注入

     为什么springboot不需要任何的注解就可以完成注入呢？
     因为springboot约定大于配置的理念，省略了大量的配置甚至注解的编写
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data=userService.login(username,password);
        //向session对象中完成对数据的绑定（session全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        //获取session中绑定的数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(ok,data);//<>里面的值可以省略不写
    }

    /*
    此类的简略方法在上面，把重复的catch的异常抽取出来变成一个类BaseController
     @RequestMapping("reg")
    public JsonResult<Void> reg(User user){
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDupcalitedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        }
        catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }
     */
}
