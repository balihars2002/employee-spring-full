package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.InventoryData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryReportDto {
    @Autowired
    private InventoryDto inventoryDto;

    @Transactional
    public List<InventoryData> getAllList() throws ApiException {
        List<InventoryData> list = inventoryDto.getAllDto();
        return list;
    }
}
