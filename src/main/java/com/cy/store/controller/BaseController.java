package com.cy.store.controller;

import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

//控制层类的基类
public class BaseController {
    //操作成功的状态码
    public static final int ok=200;

    /**
     * @ExceptionHandler注解我们一般是用来自定义异常的。
     * 可以认为它是一个异常拦截器（处理器）。
     *
     * //请求处理方法：这个方法的返回值就是要传递给前端的数据
     *     //自动将异常对象传递给此方法的参数列表上
     *     //当前项目中产生了异常，被统一拦截到此方法中，这个方法此时充当的就是请求处理方法，，方法的返回值返回给前端
     */
    @ExceptionHandler(ServiceException.class)//用于统一处理抛出的异常(？？？？？？此括号的参数不理解）,这个类不是很懂
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDupcalitedException){
            result.setState(4000);
            result.setMessage("此用户名已经被占用了");
        }
        else if (e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户数据不存在产生的异常");
        }
        else if (e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户名密码错误的异常");
        }
        else if (e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("数据更新时产生未知的异常");
        }
        else if (e instanceof InsertException){
            //设置这两个信息是为了方便返回给前端，便于做业务的判断
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }

    /**为什么要弄这个session呢？在第八集有答案
     * 获取session对象的uid
     * @param session
     * @return  当前登录用户uid的值
     */
    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登录用户的username
     * @param session session对象
     * @return 当前登录用户的用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
