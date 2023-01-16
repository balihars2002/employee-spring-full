package com.increff.employee.pojo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class ProductPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String barcode;
    private int brand_category;
    private String name;
    private double mrp;
    //default constructor
     public ProductPojo(){ }
     public ProductPojo(String BARCODE,int BRAND_CATEGORY,String NAME,double MRP){
         barcode=BARCODE;
         brand_category=BRAND_CATEGORY;
         name=NAME;
         mrp= MRP;
     }

    public int getProId(){
        return id;
    }
    public void setProId(){
        this.id = id;
    }

    public String getProBarcode() {
        return barcode;
    }

    public void setProBarcode(String brand) {
        this.barcode = brand;
    }

    public int getProCategory() {
        return brand_category;
    }

    public void setProCategory(int brand_category) {
        this.brand_category = brand_category;
    }

    public String getProName() {
        return name;
    }

    public void setProName(String name) {
        this.name = name;
    }

    public double getProMrp() {
        return mrp;
    }

    public void setProMrp(double mrp) {
        this.mrp = mrp;
    }
}
