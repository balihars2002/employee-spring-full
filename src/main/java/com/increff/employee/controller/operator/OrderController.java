package com.increff.employee.controller.operator;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/operator/order")
public class OrderController {
    @Autowired
    private OrderDto orderDto;

    @ApiOperation(value = "Add an order")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody List<OrderItemForm> orderItemFormList) throws ApiException {
        orderDto.add(orderItemFormList);
    }


    @ApiOperation(value = "View All Orders")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<OrderData> view() throws ApiException {
       return  orderDto.getAll();
    }
    @ApiOperation(value = "View Order with Given ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public List<OrderItemData> viewOrderWithGivenId(@PathVariable Integer id) throws ApiException {
        return  orderDto.viewOrderItemsInOrder(id);
    }

    @ApiOperation(value = "Generated Invoice")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void generateInvoice(@PathVariable Integer id) throws ApiException {
          orderDto.generateInvoice(id);
    }

    @ApiOperation(value = "Download Invoice")
    @GetMapping(path = "/invoice/{id}")
    public ResponseEntity<byte[]> getPDF(@PathVariable Integer id) throws Exception{
        return orderDto.getPDF(id);

    }

}
