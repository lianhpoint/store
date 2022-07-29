package com.cy.store.controller;

import com.cy.store.controller.ex.*;
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
     *
     * @ExceptionHandler作用是描述当前的方法可以处理什么样的异常
     */
    @ExceptionHandler({ServiceException.class,FileUploadException.class})//用于统一处理抛出的异常(？？？？？？此括号的参数不理解）,这个类不是很懂
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDupcalitedException){
            result.setState(4000);
            result.setMessage("此用户名已经被占用了");
        }
        else if (e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("用户数据不存在产生的异常");
        }
        else if (e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("用户名密码错误的异常");
        }
        else if (e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限异常");
        }
        else if (e instanceof AddressNotFoundException){
            result.setState(4004);
            result.setMessage("用户的收货地址不存在的异常");
        }
        else if (e instanceof AccessDeniedException){
            result.setState(4005);
            result.setMessage("收货地址数据非法访问异常");
        }
        else if (e instanceof InsertException){
            //设置这两个信息是为了方便返回给前端，便于做业务的判断
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        else if (e instanceof DeleteException){
            //设置这两个信息是为了方便返回给前端，便于做业务的判断
            result.setState(5001);
            result.setMessage("删除数据时产生未知的异常");
        }
        else if (e instanceof FileEmptyException) {
            result.setState(6000);
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
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
