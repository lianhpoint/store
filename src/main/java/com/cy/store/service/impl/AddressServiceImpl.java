package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

//新增收货地址的实现类
@Service
public class AddressServiceImpl implements IAddressService {
    @Autowired
    private AddressMapper addressMapper;

    //在添加用户的收货地址的业务层依赖于IDistrictService的业务层接口   这里不懂？？？（23集25分之前）
    @Autowired
    private IDistrictService districtService;

    //以后想改收货地址上限的时候就比较方便了，因为是直接在配置文件里改,在配置文件里有对应的语句
    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //调用收货地址统计的方法
        //第一步先判断收货地址的上限有没有达到20
        Integer count = addressMapper.countByUid(uid);
        if(count>=maxCount){
            throw new AddressCountLimitException("用户收货地址超出上限");
        }

        //对address对象的数据进行补全:省市区  这里不懂？？？（23集25分）
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //设置uid是为了以后在展示数据时知道展示哪条数据，不然就不知道这条收货地址属于哪条用户
        //address里面有两个数据需要我们指定(补全)，uid，is_delete
        address.setUid(uid);
        Integer idDefault=count==0?1:0;//判断是否是默认地址
        address.setIsDefault(idDefault);

        //补全四项收货地址
        //修改者，修改时间，创建者，创建时间这四项数据是日志信息，作用是为了以后出bug的时候减小排错的压力
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if(rows!=1){
                throw new InsertException("插入用户的收货地址时产生未知的异常");
        }

    }
}
