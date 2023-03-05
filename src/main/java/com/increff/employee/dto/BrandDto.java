package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;


import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.BrandApi;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandDto extends HelperDto {

    @Autowired
    private BrandApi brandApi;

    public void add(BrandForm form) throws ApiException {
        validateBrandForm(form);
        normalizeBrandForm(form);
        BrandPojo brandPojo = convertBrandFormToPojo(form);
        normalizeBrandPojo(brandPojo);
         getCheckBrand(brandPojo.getBrand(),brandPojo.getCategory());
         brandApi.add(brandPojo);
    }

    public List<List<String>> addList(List<BrandForm> formList) throws ApiException{
        if(formList.size()>5000){
            throw new ApiException("Max Upload Limit: 5000");
        }
        List<List<String>> errorList = new ArrayList<>();
        for(BrandForm brandForm : formList) {
            try {
                add(brandForm);
            } catch(ApiException e)
            {
                List<String> temp = new ArrayList<>();
                temp.add(brandForm.getBrand());
                temp.add(brandForm.getCategory());
                temp.add(e.getMessage());
                errorList.add(temp);
            }
        }
        return errorList;
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
        normalizeBrandForm(brandform);
        BrandPojo brandPojo = convertBrandFormToPojo(brandform);
        getCheckBrand(brandPojo.getBrand(),brandPojo.getCategory());
        normalizeBrandPojo(brandPojo);
        brandApi.update(id,brandPojo);
    }
    public BrandPojo getCheckById(Integer id) throws ApiException {
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
