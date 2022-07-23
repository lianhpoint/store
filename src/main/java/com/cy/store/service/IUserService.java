package com.cy.store.service;

import com.cy.store.entity.User;

//用户模块业务层接口
public interface IUserService {
    /**
     * 用户注册方法
     *  user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username
     * @param password
     * @return当前匹配的用户数据，如果没有则返回null值
     */
    User login(String username,String password);

    //思考底层需要用到什么参数，此时在把这些参数写进去
    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);
}
