package com.increff.employee.Dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dto.BrandReportDto;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.pojo.BrandPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandReportDtoTest extends AbstractUnitTest {

    @Autowired
    private BrandReportDto brandReportDto;
    @Autowired
    private BrandDao brandDao;

    @Before
    public void beforeTest(){

    }

    @Test
    public void getAllListTest(){
        addBrand("brand","category",false);
        List<BrandData> brandDataList = brandReportDto.getAllList();
        assertEquals("brand",brandDataList.get(0).getBrand());
        assertEquals("category",brandDataList.get(0).getCategory());
        assertEquals((Boolean) false,brandDataList.get(0).getDisabled());
    }

    public Integer addBrand(String brand,String category,Boolean isDisabled){
        BrandPojo brandPojo = new BrandPojo();
        brandPojo.setBrand(brand);
        brandPojo.setCategory(category);
        brandPojo.setDisabled(isDisabled);
        brandDao.insert(brandPojo);
        List<BrandPojo> brandPojoList = brandDao.selectAll();
        return brandPojoList.get(0).getId();
    }
}
