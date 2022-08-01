package com.cy.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**购物车数据的实体类*/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(cid, cart.cid) && Objects.equals(uid, cart.uid) && Objects.equals(pid, cart.pid) && Objects.equals(price, cart.price) && Objects.equals(num, cart.num);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cid, uid, pid, price, num);
    }
}
