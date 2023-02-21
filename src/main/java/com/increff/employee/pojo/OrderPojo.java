package com.increff.employee.pojo;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
public class OrderPojo {

    @Id
    @TableGenerator(name = "order_id", pkColumnValue = "order_id", table = "table_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    private Integer id;
    private ZonedDateTime orderAddDateTime;
    private ZonedDateTime orderUpdateDateTime;
    private Boolean isInvoiceGenerated=false;
    private LocalDate addDate;

    public LocalDate getAddDate() {
        return addDate;
    }

    public void setAddDate(LocalDate addDate) {
        this.addDate = addDate;
    }

    public Boolean getInvoiceGenerated() {
        return isInvoiceGenerated;
    }

    public void setInvoiceGenerated(Boolean invoiceGenerated) {
        isInvoiceGenerated = invoiceGenerated;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
