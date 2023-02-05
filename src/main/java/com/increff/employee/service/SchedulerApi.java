package com.increff.employee.service;

import com.increff.employee.dao.SchedulerDao;
import com.increff.employee.pojo.SchedulerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchedulerApi {
    @Autowired
    SchedulerDao schedulerDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(SchedulerPojo pojo) throws ApiException{
        schedulerDao.insert(pojo);
    }

    @Transactional
    public List<SchedulerPojo> selectAll() {
        return schedulerDao.selectAll();
    }
}
