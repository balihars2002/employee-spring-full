package com.increff.employee.service;

import com.increff.employee.model.form.BrandForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BrandApiTest {
    @Autowired
    private BrandApi brandApi;

    @Test
    public void addTest(){
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandForm.setDisabled(false);


    }

}
