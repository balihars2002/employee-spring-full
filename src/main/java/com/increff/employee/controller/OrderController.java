package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "Deletes a Brand")
    @RequestMapping(path = "/order/{id}",method = RequestMethod.DELETE)

    public void delete(@PathVariable Integer id) {
        orderDto.delete(id);
    }

    @ApiOperation(value = "View All Orders")
    @RequestMapping(path = "/order", method = RequestMethod.GET)
    public List<OrderData> view(){
       return  orderDto.viewAlLOrder();
    }


//
//    @ApiOperation(value = "Updates a Brand")
//    @RequestMapping(path = "/order/{id}", method = RequestMethod.PUT)
//    public void update(@RequestBody OrderForm form) throws ApiException {
//        orderDto.updateList(form);
//    }
}
