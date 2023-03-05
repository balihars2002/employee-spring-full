package com.increff.employee.api;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OrderApi {

    @Autowired
    private OrderDao orderDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(OrderPojo orderPojo) throws ApiException{
        orderDao.insert(orderPojo);
    }
    @Transactional(rollbackFor = ApiException.class)
    public void update(Integer id) throws ApiException {
        OrderPojo orderPojo = getById(id);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        orderPojo.setUpdateDate(zonedDateTime);
        orderDao.update(orderPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void generateInvoice(Integer id) throws ApiException{
        OrderPojo orderPojo = getById(id);
        orderPojo.setInvoiceGenerated(true);
        orderDao.update(orderPojo);
    }


    @Transactional
    public OrderPojo getById(Integer id){
        return (OrderPojo) orderDao.getById(id);
    }
    @Transactional
    public List<OrderPojo> getAll(){
        return orderDao.getAll();
    }

    @Transactional
    public List<OrderPojo> getInDate(LocalDate startDate, LocalDate endDate){
        return orderDao.getInDate(startDate, endDate);
    }
    @Transactional
    public List<OrderPojo> getOrdersForScheduler(LocalDate date){
        return orderDao.getOrdersForScheduler(date);
    }
}
