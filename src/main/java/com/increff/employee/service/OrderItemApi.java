package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class OrderItemApi {


    @Autowired
    private OrderItemDao orderItemDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderItemPojo orderItemPojo){
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
    public void update(OrderItemPojo existing) throws ApiException {
        orderItemDao.update(existing);
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
