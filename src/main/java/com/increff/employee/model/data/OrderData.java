package com.increff.employee.model.data;

import java.util.List;

public class OrderData{
    private Integer id;
//    private ZonedDateTime orderAddDateTime;
//    private ZonedDateTime orderUpdateDateTime;
    private String orderUpdatedDate;
    private String orderAddDate;
    private List<OrderItemData> orderItemDataList;
    private Boolean isInvoiceGenerated=false;

    public Boolean getInvoiceGenerated() {
        return isInvoiceGenerated;
    }

    public void setInvoiceGenerated(Boolean invoiceGenerated) {
        isInvoiceGenerated = invoiceGenerated;
    }

    public String getOrderUpdatedDate() {
        return orderUpdatedDate;
    }

    public void setOrderUpdatedDate(String orderUpdatedDate) {
        this.orderUpdatedDate = orderUpdatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderItemData> getOrderItemDataList() {
        return orderItemDataList;
    }

    public void setOrderItemDataList(List<OrderItemData> orderItemDataList) {
        this.orderItemDataList = orderItemDataList;
    }

    public String getOrderAddDate() {
        return orderAddDate;
    }

    public void setOrderAddDate(String orderAddDate) {
        this.orderAddDate = orderAddDate;
    }

}

