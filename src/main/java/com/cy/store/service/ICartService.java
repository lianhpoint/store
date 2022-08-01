package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

//收货地址业务层接口
public interface ICartService {

    void addToCart(Integer uid,Integer pid,Integer amount,String username);
}
