package com.increff.employee.flowApi;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.InventoryApi;
import com.increff.employee.api.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductFlowAPi {

    @Autowired
    private ProductApi productApi;

    @Autowired
    private InventoryApi inventoryApi;

    @Transactional(rollbackFor = ApiException.class)
    public void insert(ProductPojo productPojo) throws ApiException{
        productApi.insert(productPojo);
        InventoryPojo inventoryPojo= new InventoryPojo();
        inventoryPojo.setQuantity(0);
        inventoryPojo.setProductId(productPojo.getId());
        inventoryApi.add(inventoryPojo);
    }
}
