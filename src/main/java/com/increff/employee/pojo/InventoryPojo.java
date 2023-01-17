package com.increff.employee.pojo;

import com.mysql.cj.BindValue;
import io.swagger.models.auth.In;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class InventoryPojo {
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int quantity;
    public InventoryPojo(){ }
    public InventoryPojo(int ID,int QUANTITY){
        id=ID;
        quantity=QUANTITY;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
