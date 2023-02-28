package com.increff.employee.api;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @Transactional
    public void delete(Integer id) {
        orderDao.delete(id);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void update(Integer id) throws ApiException {
        OrderPojo orderPojo = selectById(id);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        orderPojo.setUpdateDate(zonedDateTime);
        orderDao.update(orderPojo);
    }

    @Transactional(rollbackFor = ApiException.class)
    public void generateInvoice(Integer id) throws ApiException{
        OrderPojo orderPojo = selectById(id);
        orderPojo.setInvoiceGenerated(true);
        orderDao.update(orderPojo);
    }


    @Transactional
    public OrderPojo selectById(Integer id){
        return (OrderPojo) orderDao.selectBYId(id);
    }
    @Transactional
    public List<OrderPojo> selectAll(){
        return orderDao.selectAll();
    }

    @Transactional
    public List<OrderPojo> selectInDate(LocalDate startDate, LocalDate endDate){
        return orderDao.selectInDate(startDate, endDate);
    }
    @Transactional
    public List<OrderPojo> getOrdersForScheduler(LocalDate date){
        return orderDao.getOrdersForScheduler(date);
    }
}
