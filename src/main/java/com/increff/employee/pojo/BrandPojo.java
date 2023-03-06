package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"brand","category"})})
public class BrandPojo extends AbstractPojo{

    @Id
    @TableGenerator(name = "brand_id", pkColumnValue = "brand_id", table = "table_sequence")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "brand_id")
    private Integer id;
    private String brand;
    private String category;
//    private Boolean isDisabled = false;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
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

//    public Boolean getDisabled() {
//        return isDisabled;
//    }
//
//    public void setDisabled(Boolean disabled) {
//        isDisabled = disabled;
//    }
}

