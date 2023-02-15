package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class OrderItemApi {


    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductApi productApi;
    @Autowired
    private InventoryApi inventoryApi;

    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderItemPojo orderItemPojo) throws ApiException {
        InventoryPojo inventoryPojo = inventoryApi.getPojoFromProductId(orderItemPojo.getProductId());
        Integer availableQuantity = inventoryPojo.getQuantity();
        if(availableQuantity < orderItemPojo.getQuantity()){
            throw new ApiException("Not sufficient quantity of product available in inventory");
        }
//        inventoryPojo.setQuantity(availableQuantity - orderItemPojo.getQuantity());
        inventoryApi.updateInv(inventoryPojo,(availableQuantity - orderItemPojo.getQuantity()));
//        InventoryPojo inventoryPojo = getUpdatedInventoryPojo(orderItemPojo.getProductId(),orderItemPojo.getQuantity(),false);
//        Integer quantity = inventoryPojo.getQuantity();
//        inventoryApi.updateInv(inventoryPojo,quantity);
        orderItemDao.insert(orderItemPojo);
    }

    @Transactional
    public void deleteByOrderId(Integer order_id) {
        orderItemDao.deleteByOrderId(order_id);
    }

    @Transactional
    public void deleteByProductId(Integer product_id) {
        orderItemDao.deleteByProductId(product_id);
    }

    @Transactional(rollbackFor  = ApiException.class)
    public void update(Integer id,Integer updQuantity,String barcode) throws ApiException {
        OrderItemPojo orderItemPojo = getPojoFromId(id);
        ProductPojo productPojo = productApi.getPojoFromBarcode(barcode);
        orderItemPojo.setQuantity(updQuantity);
        orderItemPojo.setProductId(productPojo.getId());
        orderItemDao.update(orderItemPojo);
    }

    public List<OrderItemPojo> selectAll(){
        return orderItemDao.selectAll();
    }

    public List<OrderItemPojo> selectSome(Integer orderId){
        return orderItemDao.selectSome(orderId);
    }

    public OrderItemPojo getPojoFromId(Integer id){
        return orderItemDao.getPojoFromId(id);
    }
}
