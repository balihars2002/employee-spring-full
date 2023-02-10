package com.increff.employee.service;
import java.util.List;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class InventoryApi {
    @Autowired
    private InventoryDao inventoryDao;
    @Transactional(rollbackFor = ApiException.class)
    public void addService(InventoryPojo inventoryPojo){
            inventoryDao.insert(inventoryPojo);
    }
    @Transactional
    public List<InventoryPojo> selectAllFromService() {
        return inventoryDao.selectAlls();
    }

    @Transactional
    public void deleteService(Integer id){
        inventoryDao.delete(id);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void updateInv(InventoryPojo inventoryPojo,Integer quantity) throws ApiException {
        InventoryPojo pojo = getPojoFromId(inventoryPojo.getId());
        System.out.println("into the update service");
        pojo.setQuantity(quantity);
    }

    @Transactional
    public InventoryPojo getPojoFromId(Integer id) throws ApiException{
        InventoryPojo inventoryPojo =  inventoryDao.selectPojoById(id);
        if(inventoryPojo == null){
            throw new ApiException("Inventory with given Id does not exist.");
        }
        return inventoryPojo;
    }

    @Transactional
    public InventoryPojo getPojoFromProductId(Integer id){
        return inventoryDao.selectPojoByProductId(id);
    }


//    @Transactional(rollbackOn = ApiException.class)
//    public void deleteservice(int id){
//        inventoryDao.delete(id);
//    }
}
