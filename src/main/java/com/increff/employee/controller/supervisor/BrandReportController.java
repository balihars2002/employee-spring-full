package com.increff.employee.controller.supervisor;

import com.increff.employee.dto.BrandDto;
import com.increff.employee.dto.BrandReportDto;
import com.increff.employee.model.data.BrandData;
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
public class BrandReportController {

    @Autowired
    private BrandDto brandDto;
    @Autowired
    private BrandReportDto brandReportDto;


    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/brandReport",method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandReportDto.getAllList();
    }

    @ApiOperation(value = "Export Product Report to CSV")
    @RequestMapping(path = "/brandReport/exportCsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException {
        brandReportDto.generateCsv(response);
    }
}
