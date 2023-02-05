package com.increff.employee.dto;

import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.increff.employee.dto.DtoHelper.*;

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
//        Integer getQuantityInInventory = productDto.getQuantityFromInventoryByPID(orderItemPojo.getProductId());
//        if(getQuantityInInventory < orderItemPojo.getQuantity()){
//            throw new ApiException("Not sufficient quantity of product available in inventory");
//        }
        InventoryPojo inventoryPojo = getUpdatedInventoryPojo(orderItemPojo.getProductId(),orderItemForm.getQuantity(),false);
        inventoryApi.updateInv(inventoryPojo);
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
        OrderItemPojo orderItemPojo = orderItemApi.getPojoFromId(id);
        ProductPojo productPojo = productApi.getPojoFromBarcode(orderItemForm.getBarcode());
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemApi.update(orderItemPojo);
        OrderPojo orderPojo =  orderApi.selectById(orderItemPojo.getOrderId());
//        if(orderPojo != null){
//            System.out.println("the orderpojo is not null");
//        }
//        else{
//            System.out.println("the order pojo is null");
//        }
        long millis = System.currentTimeMillis();
        DateFormat simple = new SimpleDateFormat("dd MMM yyyy HH:mm:ss ");
        Date date = new Date(millis);
        orderPojo.setUpdatedDate(simple.format(date));
        orderApi.update(orderPojo);
    }



}
