package com.increff.employee.dto;

import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.BrandApi;
import com.increff.employee.api.InventoryApi;
import com.increff.employee.api.ProductApi;
import com.increff.employee.util.CsvFileGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.HelperDto.convertInventoryPojoToData;

@Service
public class InventoryReportDto {
    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private CsvFileGenerator csvFileGenerator;

    List<InventoryData> inventoryDataList = new ArrayList<>();


    public List<InventoryData> get(BrandForm form) throws ApiException {
        inventoryDataList.clear();
        List<BrandPojo> brandPojoList = brandApi.getByBrandCategory(form.getBrand(),form.getCategory());
        for(BrandPojo brandPojo:brandPojoList) {
            for(ProductPojo productPojo:productApi.getByBrandId(brandPojo.getId())){
                InventoryData inventoryData = convertPojoToData(inventoryApi.getByProductId(productPojo.getId()));
                inventoryDataList.add(inventoryData);
            }
        }
        return inventoryDataList;

    }
    public void generateCsv(HttpServletResponse response) throws IOException, ApiException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"inventoryReport.csv\"");

        csvFileGenerator.writeInventoryToCsv(inventoryDataList, response.getWriter());
        inventoryDataList.clear();
    }

    public InventoryData convertPojoToData(InventoryPojo inventoryPojo) throws ApiException {
        InventoryData inventoryData = convertInventoryPojoToData(inventoryPojo);
            ProductPojo productPojo= productApi.getById(inventoryPojo.getProductId());
            BrandPojo brandPojo = brandApi.getCheck(productPojo.getBrand_category());
            inventoryData.setProductId(productPojo.getId());
            inventoryData.setMrp(productPojo.getMrp());
            inventoryData.setName(productPojo.getName());
            inventoryData.setBarcode(productPojo.getBarcode());
            inventoryData.setBrand(brandPojo.getBrand());
            inventoryData.setCategory(brandPojo.getCategory());
        return inventoryData;
    }

}
