package com.increff.employee.flowApi;

import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.increff.employee.dto.HelperDto.convertOrderItemFormToPojo;

@Service
public class OrderFlowApi {

    @Autowired
    private OrderApi orderApi;
    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private OrderItemApi orderItemApi;
    @Autowired
    private ProductApi productApi;


    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderPojo orderPojo, List<OrderItemForm> orderItemForms) throws ApiException {
        orderApi.add(orderPojo);
        Integer id = orderPojo.getId();
        for(OrderItemForm orderItemForm:orderItemForms){
            OrderItemPojo orderItemPojo = convertOrderItemFormToPojo(orderItemForm,id);
            ProductPojo productPojo = productApi.getPojoFromBarcode(orderItemForm.getBarcode());
            orderItemPojo.setProductId(productPojo.getId());
            orderItemPojo.setSellingPrice(productPojo.getMrp());
            orderItemApi.add(orderItemPojo);
        }

    }
}
