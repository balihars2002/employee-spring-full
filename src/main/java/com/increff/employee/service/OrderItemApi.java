package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderItemApi {

    @Autowired
    private OrderItemDao orderItemDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderItemPojo orderItemPojo){
        orderItemDao.insert(orderItemPojo);
    }

}
