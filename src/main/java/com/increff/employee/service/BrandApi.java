package com.increff.employee.service;

import java.util.List;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandApi {

    @Autowired
    private BrandDao brandDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(BrandPojo pojo) throws ApiException {
        brandDao.insert(pojo);
    }

    @Transactional
    public void delete(Integer id) {
        brandDao.delete(id);
    }

    @Transactional
    public BrandPojo getCheck(Integer id) throws ApiException {
        BrandPojo p = brandDao.select(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return brandDao.selectAll();
    }

    @Transactional(rollbackFor  = ApiException.class)
    public void update(Integer id,BrandPojo brandPojo) throws ApiException {
        BrandPojo brandPojo1 = getCheck(id);
        checkBrandCat(brandPojo.getBrand(),brandPojo.getCategory());
        brandPojo1.setBrand(brandPojo.getBrand());
        brandPojo1.setCategory(brandPojo.getCategory());
        brandDao.update(brandPojo1);
    }

    @Transactional
    public BrandPojo getBrandCat(String brandName, String categoryName) throws ApiException{
        BrandPojo brandPojo =  brandDao.selectPojoToCheckDuplicate(brandName,categoryName);
        if(brandPojo == null){
            throw new ApiException("Brand and Category does not exist.");
        }
        return brandPojo;
    }
    @Transactional
    public BrandPojo checkBrandCat(String brandName, String categoryName) throws ApiException{
        BrandPojo brandPojo =  brandDao.selectPojoToCheckDuplicate(brandName,categoryName);
        if(brandPojo != null){
            throw new ApiException("Brand and Category already exist.");
        }
        return brandPojo;
    }
}
