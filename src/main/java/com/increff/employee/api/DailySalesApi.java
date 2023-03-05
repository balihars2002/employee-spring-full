package com.increff.employee.api;

import com.increff.employee.dao.DailySalesDao;
import com.increff.employee.pojo.DailySalesPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DailySalesApi {
    @Autowired
    private DailySalesDao dailySalesDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(DailySalesPojo pojo) throws ApiException{
        dailySalesDao.insert(pojo);
    }

    @Transactional
    public List<DailySalesPojo> getAll() {
        return dailySalesDao.getAll();
    }
}
