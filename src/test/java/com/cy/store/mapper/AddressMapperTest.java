package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;
import com.cy.store.entity.User;
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
public class AddressMapperTest {
    //爆红的原因：不是因为代码错误，而是因为idea有代码检测功能，接口是不能直接创建bean的（里面有用到动态代理技术）
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(10);
        address.setPhone("111111111111");
        address.setName("jack");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer result = addressMapper.countByUid(10);
        System.out.println(result);
    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(11);
        System.out.println(list);
    }

    @Test
    public void findByAid(){
        System.err.println(addressMapper.findByAid(9));
    }

    @Test
    public void updateNoneDefault(){
        addressMapper.updateNoneDefault(9);
    }

    @Test
    public void updateDefaultByAid(){
        addressMapper.updateDefaultByAid(9,"张安",new Date());
    }
}
