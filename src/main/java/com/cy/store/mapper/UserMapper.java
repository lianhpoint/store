package com.cy.store.mapper;

import com.cy.store.entity.User;

//用户模块的持久层接口,因为springboot约定大于配置的原因，所以只要方法名对映上了，就不需要@repository了
public interface UserMapper {
    //插入用户的数据
    Integer insert(User user);

    //查询用户数据
    User findByUsername(String username);
}
