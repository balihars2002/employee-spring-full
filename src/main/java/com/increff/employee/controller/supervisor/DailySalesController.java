package com.increff.employee.controller.supervisor;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.dto.DailySalesDto;
import com.increff.employee.model.data.DailySalesData;
import com.increff.employee.api.ApiException;
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
public class DailySalesController {
    @Autowired
    private OrderDto orderDto;

    @Autowired
    private DailySalesDto schedulerDto;

    @ApiOperation(value = "Gets all Scheduler Data")
    @RequestMapping(path = "/scheduler",method = RequestMethod.GET)
    public List<DailySalesData> get() throws ApiException {
         return schedulerDto.getAllData();
    }

}
