package com.cy.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//定义一个拦截器
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否有uid的值，有则放行，没有则重定向到login页面
     * @param request   请求对象
     * @param response  响应对象
     * @param handler   处理器（url+controller的映射）
     * @return  true:放行；false：拦截
     * 根据session的值来决定返回true或FALSE
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //可通过HttpServletRequest对象来获取session对象
        Object obj= request.getSession().getAttribute("uid");
        if(obj==null){
            //说明用户没有登录过系统，则重定向到login页面
            //此处不能在用相对路径了，但是因为在同一个项目下，所以localhost：8080可以省略
            response.sendRedirect("/web/login.html");
            //结束后续的调用
            return false;
        }
        //放行
        return true;
    }
}
