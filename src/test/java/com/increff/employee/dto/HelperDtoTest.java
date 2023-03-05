package com.increff.employee.dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;

import static org.junit.Assert.assertEquals;

public class HelperDtoTest extends AbstractUnitTest {

    @Autowired
    private HelperDto helperDto;

//    @Test
//    public void normalizeProductPojoTest(){
//        ProductPojo productPojo = new ProductPojo();
//        productPojo.setName("Name");
//        helperDto.normalizeProductPojo(productPojo);
//        assertEquals("name",productPojo.getName());
//    }


}
