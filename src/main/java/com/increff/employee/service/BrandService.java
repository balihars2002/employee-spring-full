package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService {

    @Autowired
    private BrandDao brandDao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo pojo) throws ApiException {
        brandDao.insert(pojo);
    }

    @Transactional
    public void delete(int id) {
        brandDao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException{
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return brandDao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(BrandPojo existing) throws ApiException {
        brandDao.update(existing);
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = brandDao.select(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    public BrandPojo getBrandCat(String brandName, String categoryName){
        return brandDao.selectPojoToCheckDuplicate(brandName,categoryName);
    }
    public BrandPojo getBrandCatFromiId(int id) throws ApiException{
        return brandDao.getBrandFromId(id);
    }

}
