package com.increff.employee.dto;

import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.HelperDto.*;

@Service
public class OrderItemDto{

    @Autowired
    private OrderItemApi orderItemApi;
    @Autowired
    private ProductApi productApi;

    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private OrderApi orderApi;



    public void add(OrderItemForm orderItemForm,Integer orderId) throws ApiException{
        OrderItemPojo orderItemPojo = convertOrderItemFormToPojo(orderItemForm,orderId);
        orderItemApi.add(orderItemPojo);
    }

    public void deleteByProductId(Integer product_id) {
        orderItemApi.deleteByProductId(product_id);
    }

    public void deleteByOrderId(Integer order_id) {
        orderItemApi.deleteByProductId(order_id);
    }
//    @Transactional(rollbackOn  = ApiException.class)
//    public void updateList(OrderItemForm form) throws ApiException {
//        OrderItemPojo orderItemPojo = getPojoFromOrderIdAndProductId(form);
//
//        orderItemApi.update(orderItemPojo);
//    }
    public List<OrderItemData> viewAlLOrder() throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderItemApi.selectAll();
        for(OrderItemPojo pojo:list1){
            list.add(convertOrderItemPojoToData(pojo));
        }
        return list;
    }
    public List<OrderItemData> viewAlLOrderItemsWithGivenOrderId(Integer orderId) throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderItemApi.selectSome(orderId);
        for(OrderItemPojo pojo:list1){
            OrderItemData orderItemData = convertOrderItemPojoToData(pojo);
            ProductPojo productPojo = productApi.getPojoFromId(pojo.getProductId());
            orderItemData.setBarcode(productPojo.getBarcode());
            orderItemData.setProductName(productPojo.getName());
            list.add(orderItemData);
        }
        return list;
    }

    public OrderItemData getDataById(Integer id) throws ApiException {
        OrderItemPojo orderItemPojo = orderItemApi.getPojoFromId(id);
        OrderItemData orderItemData = convertOrderItemPojoToData(orderItemPojo);
        ProductPojo productPojo = productApi.getPojoFromId(orderItemPojo.getProductId());
        orderItemData.setBarcode(productPojo.getBarcode());
        return orderItemData;
    }

    public void updateOrderItem(Integer id,OrderItemForm orderItemForm) throws ApiException {
        orderItemApi.update(id,orderItemForm.getQuantity(),orderItemForm.getBarcode());
        OrderItemPojo orderItemPojo = orderItemApi.getPojoFromId(id);
        orderApi.update(orderItemPojo.getOrderId());
    }



}
