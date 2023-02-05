package com.increff.employee.controller.supervisor;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.dto.SalesReportDto;
import com.increff.employee.model.data.SalesReportData;
import com.increff.employee.model.form.SalesReportForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/api")
public class SalesReportController {
    private static Logger logger = Logger.getLogger(SalesReportController.class);
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private SalesReportDto salesReportDto;

    @ApiOperation(value = "Gets Sales Report List")
    @RequestMapping(path = "/salesReport",method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportForm form) throws ApiException {
        logger.info("Entered Controller");
        return salesReportDto.get(form);
    }
}
