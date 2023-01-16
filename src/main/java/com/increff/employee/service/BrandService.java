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
    private BrandDao branddao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo p) throws ApiException {
        branddao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        branddao.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException{
        return getCheck(id);
    }

    @Transactional
    public List<BrandPojo> getAll() {
        return branddao.selectAll();
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(BrandPojo ex) throws ApiException {
        branddao.update(ex);
    }

    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = branddao.select(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    public BrandPojo getBrandCat(String brandName, String categoryName){
        return branddao.selectpojotocheckduplicate(brandName,categoryName);
    }
    public BrandPojo GetbrandCatfromid(int id) throws ApiException{
        return branddao.getbrandformid(id);
    }

}
