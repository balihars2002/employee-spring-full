package com.increff.employee.service;

import com.increff.employee.dto.DtoHelper;
import com.increff.employee.pojo.BrandPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
@Transactional
public class BrandApiTest extends DtoHelper {

    @Autowired
    private BrandApi brandApi;
//    @Autowired
//    private DtoHelper dtoHelper;
    @Test
    public void testAdd() throws ApiException{
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand("Brand");
        pojo.setCategory("Category");
        brandApi.add(pojo);
    }
    @Test
    public void testNormalize() throws ApiException{
        BrandPojo pojo = new BrandPojo();
        pojo.setBrand("BRAND");
        pojo.setCategory("CATEGORY");
        normalizeBrand(pojo);
        assertEquals("brand",pojo.getBrand());
    }

}
