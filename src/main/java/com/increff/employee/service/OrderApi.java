package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Transactional
    public List<OrderPojo> selectInDate(LocalDate startDate, LocalDate endDate){
        return orderDao.selectInDate(startDate, endDate);
    }
//    @Transactional
//    public List<OrderPojo> selectInDateWithCategory(LocalDate startDate, LocalDate endDate, String category){
//        return orderDao.selectInDate(startDate, endDate, category);
//    }
}
