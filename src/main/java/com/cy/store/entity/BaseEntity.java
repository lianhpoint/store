package com.cy.store.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

//作为实体类的基类
@Data
@ToString
//Serializable这个不懂
public class BaseEntity implements Serializable {
    //修改者，修改时间，创建者，创建时间这四项数据是日志信息，作用是为了以后出bug的时候减小排错的压力
    private String createdUser ;
    private Date createdTime ;
    private String modifiedUser ;
    private  Date modifiedTime ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(createdUser, that.createdUser) && Objects.equals(createdTime, that.createdTime) && Objects.equals(modifiedUser, that.modifiedUser) && Objects.equals(modifiedTime, that.modifiedTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdUser, createdTime, modifiedUser, modifiedTime);
    }
}
