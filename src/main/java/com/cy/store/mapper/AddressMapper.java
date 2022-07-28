package com.cy.store.mapper;

import com.cy.store.entity.Address;
import com.cy.store.entity.District;

import java.util.List;

//收货地址持久层的接口
public interface AddressMapper {
    /**
     * 插入用户的收货地址数据
     *
     * 插入需要传递数据，数据放在实体类中，所以需要传递一个address
     * @param address
     * @return
     */
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址数量
     * @param uid
     * @return  当前用户的收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的id查询用户的收货地址数据
     * @param uid   用户id
     * @return  收货地址数据
     */
    List<Address> findByUid(Integer uid);
}
