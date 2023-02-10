package com.increff.employee.service;

import com.increff.employee.dao.DailySalesDao;
import com.increff.employee.pojo.DailySalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DailySalesApi {
    @Autowired
    DailySalesDao schedulerDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(DailySalesPojo pojo) throws ApiException{
        schedulerDao.insert(pojo);
    }

    @Transactional
    public List<DailySalesPojo> selectAll() {
        return schedulerDao.selectAll();
    }
}
