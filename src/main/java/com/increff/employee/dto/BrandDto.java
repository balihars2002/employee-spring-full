package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;


import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandApi;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandDto extends DtoHelper{

    @Autowired
    private BrandApi brandApi;

    public void add(BrandForm form) throws ApiException {
        validateBrandForm(form);
        BrandPojo brandPojo = convertBrandFormToPojo(form);
        normalizeBrand(brandPojo);

        //check duplicacy of brand name and category
         getCheckBrand(brandPojo.getBrand(),brandPojo.getCategory());
         brandApi.add(brandPojo);
    }
    public void delete(Integer id) {
        brandApi.delete(id);
    }

    public BrandData get(Integer id) throws ApiException{
        return convertBrandPojoToData(brandApi.get(id));
    }
    public List<BrandData> getAllList() {
        List<BrandPojo> list = brandApi.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for(BrandPojo p : list){
            list2.add(convertBrandPojoToData(p));
        }
        return list2;
    }
    public void updateList(Integer id, BrandForm brandform) throws ApiException {
        BrandPojo brandPojo= convertBrandFormToPojo(brandform);
        BrandPojo brandPojo1 = brandApi.getBrandCat(brandPojo.getBrand(),brandPojo.getCategory());
        if(brandPojo1 != null){
            throw new ApiException("Brand and Category already exist");
        }
        normalizeBrand(brandPojo);
        BrandPojo updated = getCheckFromService(id);
        updated.setCategory(brandPojo.getCategory());
        updated.setBrand(brandPojo.getBrand());
        brandApi.update(updated);

    }
    public BrandPojo getCheckFromService(Integer id) throws ApiException {
        BrandPojo p = brandApi.getCheck(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    public BrandPojo getCheckBrand(String brandName, String categoryName) throws ApiException{
        BrandPojo brandPojo= brandApi.getBrandCat(brandName,categoryName);
        if(brandPojo != null){
            throw new ApiException("Brand and Category already exist.");
        }
        return brandPojo;
    }

    public void validateBrandForm(BrandForm form) throws ApiException{
        if(StringUtil.isEmpty(form.getBrand())) {
            throw new ApiException("'Brand' cannot be empty");
        }
        if(StringUtil.isEmpty(form.getCategory())){
            throw new ApiException("'Category' cannot be empty");
        }
    }

}
