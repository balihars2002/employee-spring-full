package com.increff.employee.pojo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class DailySalesPojo {
    @Id
    @TableGenerator(name = "scheduler_id", pkColumnValue = "scheduler_id", table = "table_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "scheduler_id")
    private Integer id;
    //Todo : add unique constraint for localdate
    private LocalDate localDate;
    // Todo: remove this
    private String date;
    private Integer invoiced_orders_count;
    private Integer invoiced_items_count;
    private Double total_revenue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getInvoiced_orders_count() {
        return invoiced_orders_count;
    }

    public void setInvoiced_orders_count(Integer invoiced_orders_count) {
        this.invoiced_orders_count = invoiced_orders_count;
    }

    public Integer getInvoiced_items_count() {
        return invoiced_items_count;
    }

    public void setInvoiced_items_count(Integer invoiced_items_count) {
        this.invoiced_items_count = invoiced_items_count;
    }

    public Double getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(Double total_revenue) {
        this.total_revenue = total_revenue;
    }
}
