package com.increff.employee.Dto;

import com.increff.employee.dto.BrandDto;
import com.increff.employee.dto.InventoryReportDto;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryApi;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class InventoryReportDtoTest {

    @Autowired
    private InventoryReportDto inventoryReportDto;

    @Autowired
    private InventoryApi inventoryApi;

    @Autowired
    private BrandDto brandDto;
    @Autowired
    private ProductDto productDto;
    @Test
    public void getAllListTest() throws ApiException{
        BrandForm brandForm = new BrandForm();
        brandForm.setCategory("category");
        brandForm.setBrand("brand");
        brandForm.setDisabled(false);
        brandDto.add(brandForm);
        ProductForm productForm = new ProductForm();
        productForm.setName("name");
        productForm.setBarcode("barcode");
        productForm.setMrp(10.5);
        productForm.setBrand("brands");
        productForm.setCategory("category");
        productDto.addDto(productForm);
//        List<InventoryData> inventoryDataList = InventoryReportDto.getAllList();
    }
}
