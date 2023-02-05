package com.increff.employee.pojo;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = )})
public class OrderPojo {

    @Id
    @TableGenerator(name = "order_id", pkColumnValue = "order_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    private Integer id;
    @NotNull
    private LocalDateTime orderAddDateTime;
    private LocalDateTime orderUpdateDateTime;

    private String date;

    private String updatedDate;

    public LocalDateTime getOrderAddDateTime() {
        return orderAddDateTime;
    }

    public void setOrderAddDateTime(LocalDateTime orderAddDateTime) {
        this.orderAddDateTime = orderAddDateTime;
    }

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

    public LocalDateTime getOrderUpdateDateTime() {
        return orderUpdateDateTime;
    }

    public void setOrderUpdateDateTime(LocalDateTime orderUpdateDateTime) {
        this.orderUpdateDateTime = orderUpdateDateTime;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
