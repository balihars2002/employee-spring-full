package com.increff.employee.model.form;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.increff.employee.model.data.OrderItemData;
import java.time.LocalDate;
import java.util.List;

public class InvoiceForm {

// Todo remove this id if not getting used
    private Integer id;
    private Integer orderId;
    private String addDate;
    private Integer totalItems;
    private Double totalCost;
    private List<OrderItemData> orderItemDataList;
    public List<OrderItemData> getOrderItemDataList() {
        return orderItemDataList;
    }

    public void setOrderItemDataList(List<OrderItemData> orderItemDataList) {
        this.orderItemDataList = orderItemDataList;
    }

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
    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
