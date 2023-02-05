package com.increff.employee.Dto;

import com.increff.employee.AbstractUnitTest;
import com.increff.employee.service.ProductApi;
import com.increff.employee.dto.BrandDto;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.service.ApiException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductTestDto extends AbstractUnitTest {
    @Autowired
    ProductDto productDto;

    @Autowired
    ProductApi productApi;

    @Autowired
    BrandDto brandDto;
    @Test
    public void addProductTest() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand("brand");
        brandForm.setCategory("category");
        brandDto.add(brandForm);
        ProductForm productForm = new ProductForm();
        productForm.setName("name");
        productForm.setBarcode("barcode");
        productForm.setMrp(10.5);
        productForm.setBrand("brand");
        productForm.setCategory("category");
        productDto.addDto(productForm);
        List<ProductData> list = productDto.getAllDto();
        assertEquals("brand",list.get(0).getBrand());
        assertEquals("barcode",list.get(0).getBarcode());
        assertEquals((Double) 10.5,list.get(0).getMrp());
        assertEquals("name",list.get(0).getName());
        assertEquals("category",list.get(0).getCategory());
    }

    @Test
    public void addProductTest1() throws ApiException {
//        BrandForm brandForm = new BrandForm();
//        brandForm.setBrand("brand");
//        brandForm.setCategory("category");
//        brandDto.add(brandForm);
        ProductForm productForm = new ProductForm();
        productForm.setName("name");
        productForm.setBarcode("barcode");
        productForm.setMrp(10.5);
        productForm.setBrand("brand");
        productForm.setCategory("category");
        Boolean temp = false;
        try{
            productDto.addDto(productForm);
        }
        catch (Exception e) {
            temp = true;
        }
        List<ProductData> list = productDto.getAllDto();
        assertEquals((Boolean) true ,temp);
    }

}
