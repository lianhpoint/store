package com.cy.store.service;


import com.cy.store.entity.Address;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest//表示当前类是一个测试类，不会随同项目一起打包
/**
 * @RunWith表示启动这个单元测试类，(单元测试类是不能运行的，项目创建时自带的不用写这个类，自己写的测试类需要写这个注解)
 * 需要传递一个参数，这个参数必须是SpringRunner.class
 */
@RunWith(SpringRunner.class)
public class CartServiceTest {

    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        cartService.addToCart(11, 10000002, 5, "Tom");
    }

}
