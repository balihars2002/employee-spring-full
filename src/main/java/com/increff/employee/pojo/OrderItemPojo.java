package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
public class OrderItemPojo {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)

    @TableGenerator(name = "orderItem_id", pkColumnValue = "orderItem_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "orderItem_id")
    private int id;
    private int orderId;
    private int productId;
    private int quantity;
    private double sellingPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
