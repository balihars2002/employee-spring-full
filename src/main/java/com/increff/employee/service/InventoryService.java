package com.increff.employee.service;
import java.util.List;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InventoryService {
    @Autowired
    private InventoryDao inventoryDao;
    @Transactional(rollbackOn = ApiException.class)
    public void addservice(InventoryPojo p){
            inventoryDao.insert(p);
    }
    @Transactional
    public List<InventoryPojo> selectAllservice() {
        return inventoryDao.selectAlls();
    }

//    @Transactional(rollbackOn = ApiException.class)
//    public void deleteservice(int id){
//        inventoryDao.delete(id);
//    }
}
