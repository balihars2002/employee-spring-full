package com.increff.employee.controller;

import com.increff.employee.dto.InventoryReportDto;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.InventoryData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/api")
public class InventoryReportController {
    @Autowired
    private InventoryReportDto inventoryReportDto;

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/inventoryReport",method = RequestMethod.GET)
    public List<InventoryData> getAll()throws ApiException {
        return inventoryReportDto.getAllList();
    }
}
