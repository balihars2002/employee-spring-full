package com.increff.employee.pojo;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
public class OrderPojo extends AbstractPojo{

    @Id
    @TableGenerator(name = "order_id", pkColumnValue = "order_id", table = "table_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    private Integer id;
    private Boolean isInvoiceGenerated = false;
    private LocalDate orderLocalTime;

    public LocalDate getOrderLocalTime() {
        return orderLocalTime;
    }

    public void setOrderLocalTime(LocalDate orderLocalTime) {
        this.orderLocalTime = orderLocalTime;
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

}
