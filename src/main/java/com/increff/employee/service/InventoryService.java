package com.increff.employee.service;
import java.util.List;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;
    @Transactional(rollbackOn = ApiException.class)
    public void addService(InventoryPojo p){
            inventoryDao.insert(p);
    }
    @Transactional
    public List<InventoryPojo> selectAllFromService() {
        return inventoryDao.selectAlls();
    }

    @Transactional
    public void deleteService(int id){
        inventoryDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void updateInv(InventoryPojo inventoryPojo){
        inventoryDao.update(inventoryPojo);
    }

    @Transactional
    public InventoryPojo getPojoFromId(int id){
        return inventoryDao.selectPojoById(id);
    }

//    @Transactional(rollbackOn = ApiException.class)
//    public void deleteservice(int id){
//        inventoryDao.delete(id);
//    }
}
