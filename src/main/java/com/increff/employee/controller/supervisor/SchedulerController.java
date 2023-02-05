package com.increff.employee.controller.supervisor;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.dto.SchedulerDto;
import com.increff.employee.model.data.SchedulerData;
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
public class SchedulerController {
    @Autowired
    private OrderDto orderDto;

    @Autowired
    private SchedulerDto schedulerDto;

    @ApiOperation(value = "Gets all Scheduler Data")
    @RequestMapping(path = "/scheduler",method = RequestMethod.GET)
    public List<SchedulerData> get() throws ApiException {
         return schedulerDto.getAllData();
    }

}
