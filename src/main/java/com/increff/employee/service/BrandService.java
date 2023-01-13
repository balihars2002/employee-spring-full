package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.util.StringUtil;
@Service
public class BrandService {

    @Autowired
    private BrandDao dao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo p) throws ApiException {
        normalize(p);
        if(StringUtil.isEmpty(p.getBrand())) {
            throw new ApiException("'Brand' cannot be empty");
        }
        if(StringUtil.isEmpty(p.getCategory())){
            throw new ApiException("'Category' cannot be empty");
        }
        //check duplicacy of brand name and category
        if(getBrandCat(p.getBrand(),p.getCategory())!=null){
            throw new ApiException("Similar brand: " + p.getBrand() + " and category: " + p.getCategory() + " already existss" );
        }
        dao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        dao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException{
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return dao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException {
        normalize(p);
        BrandPojo ex = getCheck(id);
        ex.setCategory(p.getCategory());
        ex.setBrand(p.getBrand());
        dao.update(ex);
    }
    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Employee with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
    }
    public BrandPojo getBrandCat(String brandName, String categoryName){
        return dao.select(brandName,categoryName);
    }

}