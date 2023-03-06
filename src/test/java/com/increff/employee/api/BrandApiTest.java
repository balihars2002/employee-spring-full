package com.increff.employee.api;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandApiTest extends AbstractUnitTest {
    @Autowired
    private BrandApi brandApi;

    @Test
    public void addTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals("brand", brandPojoList.get(0).getBrand());
        assertEquals("category", brandPojoList.get(0).getCategory());
    }

    @Test
    public void addTest1() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("Brand");
        brandPojo.setCategory("Category");
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals("Brand", brandPojoList.get(0).getBrand());
        assertEquals("Category", brandPojoList.get(0).getCategory());
    }


    @Test
    public void getCheckTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        Integer id = brandPojoList.get(0).getId();
        BrandPojo brandPojo1 = brandApi.getCheck(id);
        assertEquals("brand", brandPojoList.get(0).getBrand());
        assertEquals("category", brandPojoList.get(0).getCategory());
    }

    @Test(expected = ApiException.class)
    public void getCheckTest1() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
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
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setCategory("category2");
        brandPojo1.setBrand("brand2");
        brandApi.add(brandPojo);
        brandApi.add(brandPojo1);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals(2, brandPojoList.size());
    }

    @Test(expected = Exception.class)
    public void getAllTest1() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setCategory("category1");
        brandPojo1.setBrand("brand1");
        brandApi.add(brandPojo);
        brandApi.add(brandPojo1);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        assertEquals(1, brandPojoList.size());
    }

    @Test
    public void getBrandCatTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        BrandPojo brandPojo1 = brandApi.getBrandCat(brandPojoList.get(0).getBrand(), brandPojoList.get(0).getCategory());
        assertEquals(brandPojo1, brandPojoList.get(0));
    }

    @Test(expected = ApiException.class)
    public void getBrandCatTest1() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        BrandPojo brandPojo1 = brandApi.getBrandCat("b", "c");
    }

    @Test
    public void updateTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();
        BrandPojo brandPojo1 = new BrandPojo();
        brandPojo1.setCategory("category2");
        brandPojo1.setBrand("brand2");
        brandApi.update(brandPojoList.get(0).getId(), brandPojo1);
        List<BrandPojo> brandPojoList1 = brandApi.getAll();
        assertEquals("brand2", brandPojoList1.get(0).getBrand());
        assertEquals("category2", brandPojoList1.get(0).getCategory());
    }

    @Test
    public void checkBrandCatTest() throws ApiException {
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setCategory("category1");
        brandPojo.setBrand("brand1");
        brandApi.add(brandPojo);
        List<BrandPojo> brandPojoList = brandApi.getAll();

    }
}

