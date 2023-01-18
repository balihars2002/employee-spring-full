package com.increff.employee.model;

import com.sun.istack.NotNull;

public class BrandForm {
    @NotNull
    private String brand;
    @NotNull
    private String category;
    private Boolean isDisabled=false;

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
    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
    public boolean getDisabled(){
        return isDisabled;
    }

    public boolean isDisabled() {
        return isDisabled;
    }
}
