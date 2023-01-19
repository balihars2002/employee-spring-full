package com.increff.employee.dto;

import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemApi;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderItemDto {

    @Autowired
    private OrderItemApi orderItemApi;

//    @Autowired
//    private ProductDto productDto;

    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemForm orderItemForm,int orderId) throws ApiException{
        OrderItemPojo orderItemPojo = convertFormToPojo(orderItemForm,orderId);
        orderItemApi.add(orderItemPojo);
    }
    public OrderItemPojo convertFormToPojo(OrderItemForm orderItemForm,int id) throws ApiException{
        ProductPojo productPojo = productService.getPojoFromBarcode(orderItemForm.getBarcode());
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(id);
        orderItemPojo.setProductId(productPojo.getProId());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemPojo.setSellingPrice(productPojo.getProMrp());
        return orderItemPojo;
    }
}
