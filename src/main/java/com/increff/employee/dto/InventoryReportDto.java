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
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
        List<InventoryPojo> inventoryPojoList = inventoryApi.getAll();

        HashMap<List<String>,InventoryData> map = new HashMap<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList){
            InventoryData data = convertPojoToData(inventoryPojo);
            if(!StringUtil.isEmpty(form.getBrand()) && (!StringUtil.isEmpty(form.getCategory()))){
                if(form.getBrand().equals(data.getBrand()) && form.getCategory().equals(data.getCategory())){
                    inventoryDataList.add(data);
                }
            }
            else if(!StringUtil.isEmpty(form.getBrand())){
                if(form.getBrand().equals(data.getBrand())){
                    inventoryDataList.add(data);
                }
            }
            else if(!StringUtil.isEmpty(form.getCategory())){
                if( form.getCategory().equals(data.getCategory())){
                    inventoryDataList.add(data);
                }
            }
            else{
                inventoryDataList.add(data);
            }
        }
        return  inventoryDataList;
    }
    public void generateCsv(HttpServletResponse response) throws IOException, ApiException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"inventoryReport.csv\"");

        csvFileGenerator.writeInventoryToCsv(inventoryDataList, response.getWriter());
        inventoryDataList.clear();
    }
    
    public InventoryData convertPojoToData(InventoryPojo inventoryPojo) throws ApiException {
        InventoryData inventoryData = convertInventoryPojoToData(inventoryPojo);
            ProductPojo productPojo= productApi.givePojoById(inventoryPojo.getProductId());
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
