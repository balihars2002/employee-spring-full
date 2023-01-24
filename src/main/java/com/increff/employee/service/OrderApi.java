package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderApi {

    @Autowired
    private OrderDao orderDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderPojo orderPojo) throws ApiException{
        orderDao.insert(orderPojo);
    }

    @Transactional
    public void delete(Integer id) {
        orderDao.delete(id);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(OrderPojo existing) throws ApiException {
        orderDao.update(existing);
    }

    @Transactional
    public List<OrderPojo> selectAll(){
        return orderDao.selectAll();
    }
}
