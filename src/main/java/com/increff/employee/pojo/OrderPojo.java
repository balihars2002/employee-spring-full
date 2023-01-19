package com.increff.employee.pojo;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = )})
public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

//    @TableGenerator(name = "order_id", pkColumnValue = "order_id")
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_id")
    private int id;
    private LocalDateTime dateTime;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
