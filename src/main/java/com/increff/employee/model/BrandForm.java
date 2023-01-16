package com.increff.employee.model;

import com.sun.istack.NotNull;

public class BrandForm {
    //@NotNull
    private String brand;
    private String category;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

}
