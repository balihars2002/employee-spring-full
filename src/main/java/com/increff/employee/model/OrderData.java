package com.increff.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class OrderData{
    private Integer id;

    private LocalDateTime localDateTime;
    private List<OrderItemData> orderItemDataList;
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

    public List<OrderItemData> getOrderItemDataList() {
        return orderItemDataList;
    }

    public void setOrderItemDataList(List<OrderItemData> orderItemDataList) {
        this.orderItemDataList = orderItemDataList;
    }
}

