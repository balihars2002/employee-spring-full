package com.increff.employee.model;

public class ProductForm {
    private String barcode;
    private  String brand;
    private String category;
    private String name;
    private double mrp;
    //barcode
    public String getProBarcode(){
        return barcode;
    }
    public void setProBarcode(String barcode){
        this.barcode=barcode;
    }
    // brand
    public String getProBrand() {
        return brand;
    }

    public void setProBrand(String brand) {
        this.brand = brand;
    }
    //category
    public String getProCategory(){
        return category;
    }
    public void setProCategory(String category){
        this.category = category;
    }
    //name
    public String getProName(){
        return name;
    }
    public void setProName(String name){
        this.name = name;
    }

    public double getProMrp(){
        return mrp;
    }
    public void setProMrp(double mrp){
        this.mrp = mrp;
    }
}


