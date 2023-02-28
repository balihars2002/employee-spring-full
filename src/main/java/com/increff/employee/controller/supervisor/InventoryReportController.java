package com.increff.employee.controller.supervisor;

import com.increff.employee.dto.InventoryReportDto;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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


    @ApiOperation(value = "Gets Brand Report List")
    @RequestMapping(path = "/inventoryReport",method = RequestMethod.POST)
    public List<InventoryData> get(@RequestBody BrandForm form) throws ApiException {
        return inventoryReportDto.get(form);
    }

    @ApiOperation(value = "Export Product Report to CSV")
    @RequestMapping(path = "/inventoryReport/exportCsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException, ApiException {
        inventoryReportDto.generateCsv(response);
    }
}
