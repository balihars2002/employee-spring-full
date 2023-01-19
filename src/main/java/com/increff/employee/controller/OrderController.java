package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value = "Add an order")
    @RequestMapping(path = "/order", method = RequestMethod.POST)
    public void add(@RequestBody OrderForm orderForm) throws ApiException {
        orderDto.add(orderForm);
    }

}
