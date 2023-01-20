package com.increff.employee.controller;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.*;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @ApiOperation(value = "Deletes an order item by Product Id")
    @RequestMapping(path = "/orderItem/id/{id}",method = RequestMethod.DELETE)

    public void deleteByProductId(@PathVariable int product_id) {
        orderItemDto.deleteByProductId(product_id);
    }

    @ApiOperation(value = "Deletes an order item by Order Id")
    @RequestMapping(path = "/orderItem//{id}",method = RequestMethod.DELETE)

    public void deleteByOrderId(@PathVariable int order_id) {
        orderItemDto.deleteByOrderId(order_id);
    }

    @ApiOperation(value = "View All Item Orders")
    @RequestMapping(path = "/orderItem", method = RequestMethod.GET)
    public List<OrderItemData> view(){
        return  orderItemDto.viewAlLOrder();
    }

//    @ApiOperation(value = "Updates a Brand")
//    @RequestMapping(path = "/brand/{id}", method = RequestMethod.PUT)
//    public void update(@RequestBody OrderItemForm form) throws ApiException {
//        orderItemDto.updateList(form);
//    }

}
