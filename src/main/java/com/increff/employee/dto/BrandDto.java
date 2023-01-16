package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BrandDto {

    @Autowired
    private BrandService brandService;

    public void add(BrandForm f) throws ApiException {
        BrandPojo brandPojo= convert(f);
        brandnormalize(brandPojo);
        if(StringUtil.isEmpty(brandPojo.getBrand())) {
            throw new ApiException("'Brand' cannot be empty");
        }
        if(StringUtil.isEmpty(brandPojo.getCategory())){
            throw new ApiException("'Category' cannot be empty");
        }
        //check duplicacy of brand name and category
        if(checkduplicate(brandPojo.getBrand(),brandPojo.getCategory())!=null){
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
    public List<BrandData> getAlllist() {
        List<BrandPojo> list = brandService.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for(BrandPojo p : list){
            list2.add(convert(p));
        }
        return list2;
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void updatelist(int id, BrandForm f) throws ApiException {
        BrandPojo brandPojo= convert(f);
        brandnormalize(brandPojo);
        BrandPojo ex = getCheckfromservice(id);
        ex.setCategory(brandPojo.getCategory());
        ex.setBrand(brandPojo.getBrand());
        brandService.update(ex);

    }
    @Transactional
    public BrandPojo getCheckfromservice(int id) throws ApiException {
        BrandPojo p = brandService.getCheck(id);
        if (p == null) {
            throw new ApiException("Brand with given ID does not exit, id: " + id);
        }
        return p;
    }
    private static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        return d;
    }
    private static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }
    protected static void brandnormalize(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase((p.getCategory())));
    }

    public BrandPojo checkduplicate(String brandName, String categoryName) throws ApiException{
        BrandPojo bp= brandService.getBrandCat(brandName,categoryName);
        if(bp != null){
            throw new ApiException("Brand and Category already exist.");
        }
        return bp;
    }
//    public BrandPojo GetbrandCatfromids(int id) throws ApiException{
//        BrandPojo brandpojo= brandService.GetbrandCatfromid(int id);
//        if(brandpojo == null){
//            throw new ApiException("The brand and Category does not exist");
//        }
//        return brandpojo;
//    }

}
