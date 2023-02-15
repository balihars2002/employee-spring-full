package com.increff.employee.model.data;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderData{
    private Integer id;
    private ZonedDateTime orderAddDateTime;
    private ZonedDateTime orderUpdateDateTime;
    private String updatedDate;
    private String date;
    private List<OrderItemData> orderItemDataList;
    private Boolean isInvoiceGenerated=false;

    public Boolean getInvoiceGenerated() {
        return isInvoiceGenerated;
    }

    public void setInvoiceGenerated(Boolean invoiceGenerated) {
        isInvoiceGenerated = invoiceGenerated;
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

