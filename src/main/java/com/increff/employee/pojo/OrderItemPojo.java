package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
public class OrderItemPojo {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)

    @TableGenerator(name = "orderItem_id", pkColumnValue = "orderItem_id", table = "table_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "orderItem_id")
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Double sellingPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
