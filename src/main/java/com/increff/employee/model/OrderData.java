package com.increff.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class OrderData{
    private Integer id;

    private LocalDateTime localDateTime;
    private List<OrderItemForm> orderItemFormList;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public List<OrderItemForm> getOrderItemFormList() {
        return orderItemFormList;
    }

    public void setOrderItemFormList(List<OrderItemForm> orderItemFormList) {
        this.orderItemFormList = orderItemFormList;
    }
}
