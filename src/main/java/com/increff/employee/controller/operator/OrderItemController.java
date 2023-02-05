package com.increff.employee.controller.operator;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
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

    public void deleteByProductId(@PathVariable Integer product_id) {
        orderItemDto.deleteByProductId(product_id);
    }

    @ApiOperation(value = "Deletes an order item by Order Id")
    @RequestMapping(path = "/orderItem/{id}",method = RequestMethod.DELETE)

    public void deleteByOrderId(@PathVariable Integer order_id) {
        orderItemDto.deleteByOrderId(order_id);
    }

    @ApiOperation(value = "View All Item Orders")
    @RequestMapping(path = "/orderItem", method = RequestMethod.GET)
    public List<OrderItemData> viewData() throws ApiException {
        return  orderItemDto.viewAlLOrder();
    }

    @ApiOperation(value = "Update Item Order")
    @RequestMapping(path = "/orderItem/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id,@RequestBody OrderItemForm form) throws ApiException {
        orderItemDto.updateOrderItem(id,form);
    }
    @ApiOperation(value = "View Order Item by Id")
    @RequestMapping(path = "/orderItem/{id}", method = RequestMethod.GET)
    public OrderItemData view(@PathVariable Integer id) throws ApiException {
        return  orderItemDto.getDataById(id);
    }

}
