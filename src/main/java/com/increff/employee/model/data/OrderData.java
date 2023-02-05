package com.increff.employee.model.data;

import java.time.LocalDateTime;
import java.util.List;

public class OrderData{
    private Integer id;
  //  private LocalDateTime orderAddDateTime;
    private LocalDateTime orderUpdateDateTime;
    private String updatedDate;
    private String date;
    private List<OrderItemData> orderItemDataList;

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LocalDateTime getOrderUpdateDateTime() {
        return orderUpdateDateTime;
    }

    public void setOrderUpdateDateTime(LocalDateTime orderUpdateDateTime) {
        this.orderUpdateDateTime = orderUpdateDateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public LocalDateTime getOrderAddDateTime() {
//        return orderAddDateTime;
//    }
//
//    public void setOrderAddDateTime(LocalDateTime orderAddDateTime) {
//        this.orderAddDateTime = orderAddDateTime;
//    }

    public List<OrderItemData> getOrderItemDataList() {
        return orderItemDataList;
    }

    public void setOrderItemDataList(List<OrderItemData> orderItemDataList) {
        this.orderItemDataList = orderItemDataList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

