package com.increff.employee.pojo;


import javax.persistence.*;

@Entity
public class ProductPojo {
    @Id

    @TableGenerator(name = "product_id", pkColumnValue = "product_id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "product_id")
    private Integer id;
    private String barcode;
    private Integer brand_category;
    private String name;
    private Double mrp;
    //default constructor
     public ProductPojo(){ }
    public ProductPojo(String BARCODE, Integer BRAND_CATEGORY, String NAME, Double MRP){
        barcode=BARCODE;
        brand_category=BRAND_CATEGORY;
        name=NAME;
        mrp= MRP;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getBrand_category() {
        return brand_category;
    }

    public void setBrand_category(Integer brand_category) {
        this.brand_category = brand_category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }



}
