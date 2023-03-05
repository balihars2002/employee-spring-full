package com.increff.employee.dao;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.BrandPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

//import static junit.framework.TestCase.assertEquals;

public class BrandDaoTest extends AbstractUnitTest {

    @Autowired
    private BrandDao brandDao;

    @Before
    public void beforeTest(){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand("brand");
        brandPojo.setCategory("category");
        brandPojo.setDisabled(false);
        brandDao.insert(brandPojo);
    }
    @Test
    public void insertTest(){
        List<BrandPojo> brandPojoList = brandDao.getAll();
        assertEquals("brand",brandPojoList.get(0).getBrand());
        assertEquals("category",brandPojoList.get(0).getCategory());
        assertEquals((Boolean) false ,brandPojoList.get(0).getDisabled());
    }



//    @Test
//    public void deleteTest1(){
//        List<BrandPojo> brandPojoList = brandDao.selectAll();
//        brandDao.delete((brandPojoList.get(0).getId()+1));
//        List<BrandPojo> brandPojoList1 = brandDao.selectAll();
//        assertEquals(0,brandPojoList1.size());
//    }

    @Test
    public void selectTest(){
        List<BrandPojo> brandPojoList = brandDao.getAll();
        BrandPojo brandPojo = brandDao.getById(brandPojoList.get(0).getId());
        assertEquals("brand",brandPojo.getBrand());
        assertEquals("category",brandPojo.getCategory());
        assertEquals((Boolean)false,brandPojo.getDisabled());
    }



    @Test
    public void selectAllTest(){
        insertBrand("brand1","category1",false);
        List<BrandPojo> brandPojoList = brandDao.getAll();
        assertEquals("brand",brandPojoList.get(0).getBrand());
        assertEquals("category",brandPojoList.get(0).getCategory());
        assertEquals((Boolean)false,brandPojoList.get(0).getDisabled());
        assertEquals("brand1",brandPojoList.get(1).getBrand());
        assertEquals("category1",brandPojoList.get(1).getCategory());
        assertEquals((Boolean)false,brandPojoList.get(1).getDisabled());
    }


    @Test
    public void selectPojoToCheckDuplicateTest(){
        List<BrandPojo> brandPojoList = brandDao.getAll();
        BrandPojo brandPojo = brandPojoList.get(0);
        BrandPojo brandPojo1 = brandDao.checkDuplicatePojo(brandPojo.getBrand(),brandPojo.getCategory());
        assertEquals("brand",brandPojo.getBrand());
        assertEquals("category",brandPojo.getCategory());
        assertEquals((Boolean)false,brandPojo.getDisabled());
    }

    @Test
    public void updateTest(){
        List<BrandPojo> brandPojoList = brandDao.getAll();
        BrandPojo brandPojo = brandPojoList.get(0);
        brandDao.update(brandPojo);
    }


    public void insertBrand(String brand,String category,Boolean isDisabled){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandPojo.setDisabled(isDisabled);
        brandDao.insert(brandPojo);
    }



}
