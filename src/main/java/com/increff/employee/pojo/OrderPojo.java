package com.increff.employee.pojo;


import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
public class OrderPojo {

    @Id
    @TableGenerator(name = "order_id", pkColumnValue = "order_id", table = "table_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    private Integer id;
    private ZonedDateTime orderAddDateTime;
    private ZonedDateTime orderUpdateDateTime;
    private String date;
    private String updatedDate;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public ZonedDateTime getOrderAddDateTime() {
        return orderAddDateTime;
    }

    public void setOrderAddDateTime(ZonedDateTime orderAddDateTime) {
        this.orderAddDateTime = orderAddDateTime;
    }

    public ZonedDateTime getOrderUpdateDateTime() {
        return orderUpdateDateTime;
    }

    public void setOrderUpdateDateTime(ZonedDateTime orderUpdateDateTime) {
        this.orderUpdateDateTime = orderUpdateDateTime;
    }
}
