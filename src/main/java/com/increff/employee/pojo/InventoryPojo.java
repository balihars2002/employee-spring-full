package com.increff.employee.pojo;

import com.mysql.cj.BindValue;
import com.sun.istack.NotNull;
import io.swagger.models.auth.In;

import javax.persistence.*;

@Entity
public class InventoryPojo {
    @Id
    @TableGenerator(name = "inventory_id", pkColumnValue = "inventory_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "inventory_id")
    private Integer id;
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
