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
public class BrandDto extends HelperDto {

    @Autowired
    private BrandApi brandApi;

    public void add(BrandForm form) throws ApiException {
        //validate if form is not empty
        validateBrandForm(form);
        //convert brandForm to brandPojo
        BrandPojo brandPojo = convertBrandFormToPojo(form);
        //normalize brand and category
        normalizeBrandPojo(brandPojo);
        //check duplicate of brand name and category
         getCheckBrand(brandPojo.getBrand(),brandPojo.getCategory());
         //add brand
         brandApi.add(brandPojo);
    }
    public void delete(Integer id) {
        brandApi.delete(id);
    }

    public BrandData get(Integer id) throws ApiException{
        return convertBrandPojoToData(brandApi.getCheck(id));
    }
    public List<BrandData> getAllList() {
        List<BrandPojo> brandPojoList = brandApi.getAll();
        List<BrandData> brandDataList = new ArrayList<BrandData>();
        for(BrandPojo brandPojo : brandPojoList){
            brandDataList.add(convertBrandPojoToData(brandPojo));
        }
        return brandDataList;
    }
    public void updateList(Integer id, BrandForm brandform) throws ApiException {
        validateBrandForm(brandform);
        BrandPojo brandPojo = convertBrandFormToPojo(brandform);
        getCheckBrand(brandPojo.getBrand(),brandPojo.getCategory());
//        BrandPojo brandPojo1 = brandApi.getBrandCat(brandPojo.getBrand(),brandPojo.getCategory());
        normalizeBrandPojo(brandPojo);
//        BrandPojo updated = getCheckFromService(id);
//        updated.setCategory(brandPojo.getCategory());
//        updated.setBrand(brandPojo.getBrand());
        brandApi.update(id,brandPojo);

    }
    public BrandPojo getCheckFromService(Integer id) throws ApiException {
        return brandApi.getCheck(id);
    }
    public BrandPojo getCheckBrand(String brandName, String categoryName) throws ApiException{
        return brandApi.checkBrandCat(brandName,categoryName);
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
