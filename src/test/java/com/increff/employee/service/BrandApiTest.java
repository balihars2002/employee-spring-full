package com.increff.employee.service;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class BrandApiTest extends AbstractUnitTest {
    @Autowired
    private BrandApi brandApi;

    @Test
    public void addTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandPojo.setDisabled(false);
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals("brand",brandPojoList.get(0).getBrand());
        assertEquals("category",brandPojoList.get(0).getCategory());
        assertEquals((Boolean)false,brandPojoList.get(0).getDisabled());
    }
    @Test
    public void addTest1() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("Brand");
        brandPojo.setCategory("Category");
        brandPojo.setDisabled(false);
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals("Brand",brandPojoList.get(0).getBrand());
        assertEquals("Category",brandPojoList.get(0).getCategory());
        assertEquals((Boolean)false,brandPojoList.get(0).getDisabled());
    }

    @Test
    public void deleteTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("Brand");
        brandPojo.setCategory("Category");
        brandPojo.setDisabled(false);
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        Integer id = brandPojoList.get(0).getId();
        brandApi.delete(id);
        brandPojoList = brandApi.getAll();
        assertEquals(0,brandPojoList.size());
    }

    @Test
    public void getCheckTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandPojo.setDisabled(false);
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        Integer id = brandPojoList.get(0).getId();
        BrandPojo brandPojo1 = brandApi.getCheck(id);
        assertEquals("brand",brandPojoList.get(0).getBrand());
        assertEquals("category",brandPojoList.get(0).getCategory());
        assertEquals((Boolean) false ,brandPojoList.get(0).getDisabled());
    }

    @Test(expected = ApiException.class)
    public void getCheckTest1() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandPojo.setDisabled(false);
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        Integer id = brandPojoList.get(0).getId() + 1;
        BrandPojo brandPojo1 = brandApi.getCheck(id);
    }

    @Test
    public void getAllTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        brandPojo.setDisabled(false);
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setCategory("category2");
        brandPojo1.setBrand("brand2");
        brandPojo1.setDisabled(true);
        brandApi.add(brandPojo);
        brandApi.add(brandPojo1);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals(2,brandPojoList.size());
    }

    @Test(expected = Exception.class)
    public void getAllTest1() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        brandPojo.setDisabled(false);
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setCategory("category1");
        brandPojo1.setBrand("brand1");
        brandPojo1.setDisabled(false);
        brandApi.add(brandPojo);
        brandApi.add(brandPojo1);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals(1,brandPojoList.size());
    }

    @Test(expected = Exception.class)
    public void getBrandCatTest() throws  ApiException{
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        brandPojo.setDisabled(false);
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        BrandPojo brandPojo1 = brandApi.getBrandCat(brandPojoList.get(0).getBrand(),brandPojoList.get(0).getCategory());
        assertEquals(brandPojo1,brandPojoList.get(0));
    }

//    @Test
//    public void updateTest() throws ApiException{
//        BrandPojo brandPojo = new BrandPojo();
//        brandPojo.setCategory("category1");
//        brandPojo.setBrand("brand1");
//        brandPojo.setDisabled(false);
//        brandApi.add(brandPojo);
//        List<BrandPojo> brandPojoList = brandApi.getAll();
//        BrandPojo brandPojo1 = brandPojoList.get(0);
//        brandPojo1.setCategory("updatedcategory");
//        brandApi.update(brandPojo1);
//        List<BrandPojo> brandPojoList1 = brandApi.getAll();
//        assertEquals("updatedcategory",brandPojoList1.get(0).getCategory());
//    }

}

