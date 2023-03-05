package com.increff.employee.api;

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
        InventoryPojo inventoryPojo = inventoryApi.getByProductId(orderItemPojo.getProductId());
        if(inventoryPojo == null) throw new ApiException("Not sufficient quantity of product available in inventory");
        Integer availableQuantity = inventoryPojo.getQuantity();
        if(availableQuantity < orderItemPojo.getQuantity()){
            throw new ApiException("Not sufficient quantity of product available in inventory");
        }
        inventoryApi.updateInv(inventoryPojo,(availableQuantity - orderItemPojo.getQuantity()));
        orderItemDao.insert(orderItemPojo);
    }
    @Transactional
    public void deleteById(Integer id) {
        orderItemDao.deleteById(id);
    }

    @Transactional
    public void deleteByProductId(Integer product_id) {
        orderItemDao.deleteByProductId(product_id);
    }

   // Todo refactor this and break into functions
    @Transactional(rollbackFor  = ApiException.class)
    public void update(Integer id,Integer updQuantity,String barcode,Double sellingPrice) throws ApiException {
        OrderItemPojo orderItemPojo = getById(id);
        Integer initialQuantity = orderItemPojo.getQuantity();
        Integer product_id = productApi.getByBarcode(barcode).getId();
        InventoryPojo inventoryPojo = inventoryApi.getByProductId(product_id);
        Integer availableQuantity = inventoryPojo.getQuantity();
        ProductPojo productPojo = productApi.getByBarcode(barcode);
        if(sellingPrice > productPojo.getMrp()){
            throw new ApiException("Selling Price cannot be greater than Mrp");
        }
        if(updQuantity <= 0){
            throw new ApiException("Quantity cannot be zero or negative");
        }
        Integer extraQuantity = updQuantity - initialQuantity;
        if(initialQuantity < updQuantity){
            if(availableQuantity < extraQuantity){
                throw new ApiException("Unavailable Quantity");
            }
            else{
                inventoryApi.updateInv(inventoryPojo,(availableQuantity - extraQuantity));
            }
        }
        else{
            inventoryApi.updateInv(inventoryPojo,(availableQuantity - extraQuantity));
        }
        createUpdatedPojo(orderItemPojo,updQuantity,sellingPrice);
        orderItemPojo.setProductId(productPojo.getId());
        orderItemDao.update(orderItemPojo);
    }

    public void createUpdatedPojo(OrderItemPojo orderItemPojo,Integer updQuantity,Double sellingPrice){
        orderItemPojo.setSellingPrice(sellingPrice);
        orderItemPojo.setQuantity(updQuantity);
    }
    public List<OrderItemPojo> getAll(){
        return orderItemDao.getAll();
    }

    public List<OrderItemPojo> getByOrderId(Integer orderId){
        return orderItemDao.getByOrderId(orderId);
    }

    public OrderItemPojo getById(Integer id){
        return orderItemDao.getById(id);
    }


}
