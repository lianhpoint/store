package com.cy.store.mapper;

import com.cy.store.entity.User;

import java.util.Date;

//用户模块的持久层接口,因为springboot约定大于配置的原因，所以只要方法名对映上了，就不需要@repository了
public interface UserMapper {
    //插入用户的数据
    Integer insert(User user);

    //查询用户数据
    User findByUsername(String username);

    /**
     * 根据用户的id修改密码
     * @param uid
     * @param password  用户输入的新密码
     * @param modifiedUser  表示修改的执行者
     * @param modifiedTime  表示修改的时间
     * @return
     */
    Integer updatePasswordByUid(Integer uid,
                                String password,
                                String modifiedUser,
                                Date modifiedTime);

    //根据用户的id查询用户的数据
    User findByUid(Integer uid);

}
