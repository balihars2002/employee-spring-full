package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BrandDto extends DtoHelper{

    @Autowired
    private BrandService brandService;

    public void add(BrandForm form) throws ApiException {
        BrandPojo brandPojo= convert(form);
        normalizeBrand(brandPojo);
        if(StringUtil.isEmpty(brandPojo.getBrand())) {
            throw new ApiException("'Brand' cannot be empty");
        }
        if(StringUtil.isEmpty(brandPojo.getCategory())){
            throw new ApiException("'Category' cannot be empty");
        }
        //check duplicacy of brand name and category
        BrandPojo existingPojo = checkDuplicate(brandPojo.getBrand(),brandPojo.getCategory());
        if(existingPojo != null){
            throw new ApiException("Similar brand: " + brandPojo.getBrand() + " and category: " + brandPojo.getCategory() + " already existss" );
        }
         brandService.add(brandPojo);
    }
    @Transactional
    public void delete(int id) {
        brandService.delete(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public BrandData get(int id) throws ApiException{
        return convert(brandService.get(id));
    }
    @Transactional
    public List<BrandData> getAllList() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for(BrandPojo p : list){
            list2.add(convert(p));
        }
        return list2;
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void updateList(int id, BrandForm brandform) throws ApiException {
        BrandPojo brandPojo= convert(brandform);
        BrandPojo brandPojo1 = brandService.getBrandCat(brandPojo.getBrand(),brandPojo.getCategory());
        if(brandPojo1 != null){
            throw new ApiException("Brand and Category already exist");
        }
        normalizeBrand(brandPojo);
        BrandPojo ex = getCheckFromService(id);
        ex.setCategory(brandPojo.getCategory());
        ex.setBrand(brandPojo.getBrand());
        brandService.update(ex);

    }
    @Transactional
    public BrandPojo getCheckFromService(int id) throws ApiException {
        BrandPojo p = brandService.getCheck(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    public BrandPojo checkDuplicate(String brandName, String categoryName) throws ApiException{
        BrandPojo bp= brandService.getBrandCat(brandName,categoryName);
        if(bp != null){
            throw new ApiException("Brand and Category already exist.");
        }
        return bp;
    }
}
