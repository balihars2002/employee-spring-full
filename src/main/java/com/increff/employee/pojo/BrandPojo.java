package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
//@Table
public class BrandPojo {

    @Id
//     Todo: Use table generation type
//     Todo: Use box type everywhere
//     Todo: Add unique constraint
       @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String brand;
    private String category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}

