package com.increff.employee.pojo;


import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode", "brand_category"})})
public class ProductPojo extends AbstractPojo{
    @Id
    @TableGenerator(name = "product_id", pkColumnValue = "product_id", table = "table_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "product_id")
    private Integer id;
    private String barcode;
    private Integer brand_category;
    private String name;
    private Double mrp;

    //default constructor
    public ProductPojo() {
    }

    public ProductPojo(String barcode, Integer brand_category, String name, Double mrp) {
        brand_category = brand_category;
        barcode = barcode;
        name = name;
        mrp = mrp;
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
