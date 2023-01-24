package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderItemApi {


    @Autowired
    private OrderItemDao orderItemDao;

    @Transactional(rollbackOn = ApiException.class)
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

    @Transactional(rollbackOn  = ApiException.class)
    public void update(OrderItemPojo existing) throws ApiException {
        orderItemDao.update(existing);
    }

    public List<OrderItemPojo> selectAll(){
        return orderItemDao.selectAll();
    }


}
