package com.increff.employee.api;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class InventoryApi {
    @Autowired
    private InventoryDao inventoryDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(InventoryPojo inventoryPojo) {
        inventoryDao.insert(inventoryPojo);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return inventoryDao.getAll();
    }

//    @Transactional
//    public void delete(Integer id) {
//        inventoryDao.delete(id);
//    }

    @Transactional(rollbackFor = ApiException.class)
    public void updateInv(InventoryPojo inventoryPojo, Integer quantity) throws ApiException {
        InventoryPojo pojo = getById(inventoryPojo.getId());
        pojo.setQuantity(quantity);
    }

    @Transactional
    public InventoryPojo getById(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryDao.getById(id);
        if (inventoryPojo == null) {
            throw new ApiException("Inventory with given Id does not exist.");
        }
        return inventoryPojo;
    }

    @Transactional
    public InventoryPojo getByProductId(Integer id) {
        return inventoryDao.getByProductId(id);
    }

}
