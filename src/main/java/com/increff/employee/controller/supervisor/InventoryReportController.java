package com.increff.employee.controller.supervisor;

import com.increff.employee.dto.InventoryReportDto;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @ApiOperation(value = "Export Product Report to CSV")
    @RequestMapping(path = "/inventory/exportCsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException, ApiException {
        inventoryReportDto.generateCsv(response);
    }
}
