package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.Cart;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest//表示当前类是一个测试类，不会随同项目一起打包
/**
 * @RunWith表示启动这个单元测试类，(单元测试类是不能运行的，项目创建时自带的不用写这个类，自己写的测试类需要写这个注解)
 * 需要传递一个参数，这个参数必须是SpringRunner.class
 */
@RunWith(SpringRunner.class)
public class CartMapperTest {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(11);
        cart.setPid(10000001);
        cart.setNum(3);
        cart.setPrice(4L);//长整型
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid() {
        cartMapper.updateNumByCid(1, 4, "张三", new Date());
    }

    @Test
    public void findByUidAndPid() {
        Cart cart = cartMapper.findByUidAndPid(11, 10000001);
        System.out.println(cart);
    }
}

