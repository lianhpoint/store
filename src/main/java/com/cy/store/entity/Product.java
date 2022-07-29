package com.cy.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/** 商品数据的实体类 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity implements Serializable {

        private Integer id;
        private Integer categoryId;
        private String itemType;
        private String title;
        private String sellPoint;
        private Long price;
        private Integer num;
        private String image;
        private Integer status;
        private Integer priority;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                if (!super.equals(o)) return false;
                Product product = (Product) o;
                return Objects.equals(id, product.id) && Objects.equals(categoryId, product.categoryId) && Objects.equals(itemType, product.itemType) && Objects.equals(title, product.title) && Objects.equals(sellPoint, product.sellPoint) && Objects.equals(price, product.price) && Objects.equals(num, product.num) && Objects.equals(image, product.image) && Objects.equals(status, product.status) && Objects.equals(priority, product.priority);
        }

        @Override
        public int hashCode() {
                return Objects.hash(super.hashCode(), id, categoryId, itemType, title, sellPoint, price, num, image, status, priority);
        }
}
