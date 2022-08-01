package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        //查询当前要添加的这个购物车是否在表中已存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        if(result==null){//表示这个商品从来没有被添加到购物车当中，则进行添加操作
            //创建一个cart对象
            Cart cart = new Cart();
            //补全数据：参数传递的数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            //补全价格：来自于商品中的数据
            Product product = productMapper.findById(pid);
            //补全四项日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            //执行输入的插入操作
            Integer rows=cartMapper.insert(cart);
            if(rows!=1){
                throw new UpdateException("插入数据时产生未知的异常");
            }

        }else {//表示当前商品在购物车中已经存在，则更新这条数据的num值
            int num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if(rows!=1){
                throw new UpdateException("更新数据时产生未知的异常");
            }
        }
    }
}
