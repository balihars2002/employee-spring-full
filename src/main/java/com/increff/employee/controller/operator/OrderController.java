package com.increff.employee.controller.operator;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.data.OrderData;
import com.increff.employee.model.form.OrderForm;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public void add(@RequestBody List<OrderItemForm> orderItemFormList) throws ApiException {
        orderDto.add(orderItemFormList);
    }

    @ApiOperation(value = "Deletes a Brand")
    @RequestMapping(path = "/order/{id}",method = RequestMethod.DELETE)

    public void delete(@PathVariable Integer id) {
        orderDto.delete(id);
    }

    @ApiOperation(value = "View All Orders")
    @RequestMapping(path = "/order", method = RequestMethod.GET)
    public List<OrderData> view() throws ApiException {
       return  orderDto.viewAlLOrder();
    }
    @ApiOperation(value = "View Order with Given ID")
    @RequestMapping(path = "/order/{id}", method = RequestMethod.GET)
    public List<OrderItemData> viewOrderWithGivenId(@PathVariable Integer id) throws ApiException {
        return  orderDto.viewOrderItemsInOrder(id);
    }

    @ApiOperation(value = "Generated Invoice")
    @RequestMapping(path = "/order/{id}", method = RequestMethod.PUT)
    public void generateInvoice(@PathVariable Integer id) throws ApiException {
          orderDto.generateInvoice(id);
    }

    @ApiOperation(value = "Download Invoice")
    @GetMapping(path = "/order/invoice/{id}")
    public ResponseEntity<byte[]> getPDF(@PathVariable Integer id) throws Exception{
        return orderDto.getPDF(id);

    }
//    @ApiOperation(value = "Updates a Product quantity in inventory")
//    @RequestMapping(path = "/inventory/{id}", method = RequestMethod.PUT)
//    public void update(@PathVariable Integer id,@RequestBody InventoryForm inventoryForm) throws ApiException {
//        // ProductPojo p = convert(f);
//        orderDto.update(id,inventoryForm);
//    }
//
//    @ApiOperation(value = "Updates a Brand")
//    @RequestMapping(path = "/order/{id}", method = RequestMethod.PUT)
//    public void update(@RequestBody OrderForm form) throws ApiException {
//        orderDto.updateList(form);
//    }
}
