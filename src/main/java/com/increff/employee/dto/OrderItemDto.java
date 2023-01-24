package com.increff.employee.dto;

import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemApi;
import com.increff.employee.service.ProductApi;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemDto{

    @Autowired
    private OrderItemApi orderItemApi;

    @Autowired
    private ProductApi productApi;

    @Autowired
    private ProductDto productDto;
    @Autowired
    private InventoryDto inventoryDto;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemForm orderItemForm,Integer orderId) throws ApiException{
        OrderItemPojo orderItemPojo = convertFormToPojo(orderItemForm,orderId);
        Integer getQuantityInInventory = productDto.getQuantityFromInventoryByPID(orderItemPojo.getProductId());
        if(getQuantityInInventory < orderItemPojo.getQuantity()){
            throw new ApiException("Not sufficient quantity of product available in inventory");
        }
        inventoryDto.increaseOrDecreaseInventory(orderItemPojo.getProductId(),orderItemPojo.getQuantity(),false);
        orderItemApi.add(orderItemPojo);
    }
    @Transactional
    public void deleteByProductId(Integer product_id) {
        orderItemApi.deleteByProductId(product_id);
    }
    @Transactional
    public void deleteByOrderId(Integer order_id) {
        orderItemApi.deleteByProductId(order_id);
    }
//    @Transactional(rollbackOn  = ApiException.class)
//    public void updateList(OrderItemForm form) throws ApiException {
//        OrderItemPojo orderItemPojo = getPojoFromOrderIdAndProductId(form);
//
//        orderItemApi.update(orderItemPojo);
//    }
    @Transactional
    public List<OrderItemData> viewAlLOrder(){
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderItemApi.selectAll();
        for(OrderItemPojo pojo:list1){
            list.add(convertPojoToData(pojo));
        }
        return list;
    }

//    public OrderItemPojo getPojoFromOrderIdAndProductId(OrderItemForm orderItemForm){
//        OrderItemPojo orderItemPojo = orderItemApi.getPojoFromOrderIdAndProductId(orderItemForm.ge);
//
//    }
    public OrderItemData convertPojoToData(OrderItemPojo orderItemPojo){
        OrderItemData orderItemData = new OrderItemData();
       // orderItemData.setId(orderItemPojo.getId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setProductId(orderItemPojo.getProductId());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        return orderItemData;
    }
    public OrderItemPojo convertFormToPojo(OrderItemForm orderItemForm,Integer id) throws ApiException{
        ProductPojo productPojo = productApi.getPojoFromBarcode(orderItemForm.getBarcode());
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(id);
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemPojo.setSellingPrice(productPojo.getMrp());
        return orderItemPojo;
    }
}
