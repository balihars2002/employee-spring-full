package com.increff.employee.controller.operator;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/operator/orderItem")
public class OrderItemController {

    @Autowired
    private OrderItemDto orderItemDto;

    @ApiOperation(value = "Add an order")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm orderItemForm) throws ApiException {
        orderItemDto.add(orderItemForm,0);
    }


    @ApiOperation(value = "View All Item Orders")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<OrderItemData> viewData() throws ApiException {
        return  orderItemDto.viewAlLOrderItems();
    }

    @ApiOperation(value = "Update Item Order")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id,@RequestBody OrderItemForm form) throws ApiException {
        orderItemDto.update(id,form);
    }
    @ApiOperation(value = "View Order Item by Id")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public OrderItemData view(@PathVariable Integer id) throws ApiException {
        return  orderItemDto.getDataById(id);
    }

    @ApiOperation(value = "View Order Item by Id")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) throws ApiException {
        orderItemDto.deleteById(id);
    }

}
