package com.increff.employee.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = )})
public class OrderPojo {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)

    @TableGenerator(name = "order_id", pkColumnValue = "order_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    private Integer id;
    @NotNull
    private LocalDateTime dateTime;

    private String date;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

}
