package com.increff.employee.controller.supervisor;

import com.increff.employee.dto.BrandDto;
import com.increff.employee.dto.BrandReportDto;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @ApiOperation(value = "Gets Brand Report List")
    @RequestMapping(path = "/brandReport",method = RequestMethod.POST)
    public List<BrandData> get(@RequestBody BrandForm form) throws ApiException {
        return brandReportDto.get(form);
    }


    @ApiOperation(value = "Export Product Report to CSV")
    @RequestMapping(path = "/brandReport/exportCsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException {
        brandReportDto.generateCsv(response);
    }
}
