package com.increff.employee.dto;

import com.increff.employee.model.data.InventoryData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.DtoHelper.convertInventoryPojoToData;

@Service
public class InventoryReportDto {
    @Autowired
    private InventoryApi inventoryApi;

    public List<InventoryData> getAllList() throws ApiException {
        List<InventoryPojo> inventoryPojoList = inventoryApi.selectAllFromService();
        List<InventoryData> inventoryDataList = new ArrayList<>();
        for(InventoryPojo inventoryPojo : inventoryPojoList){
            InventoryData inventoryData = convertInventoryPojoToData(inventoryPojo);
            inventoryDataList.add(inventoryData);
        }
        return inventoryDataList;
    }
}
