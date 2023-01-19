package com.increff.employee.controller;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/api")
public class OrderItemController {

    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "Add an order")
    @RequestMapping(path = "/orderItem", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm orderItemForm) throws ApiException {
        orderItemDto.add(orderItemForm,0);
    }
}
