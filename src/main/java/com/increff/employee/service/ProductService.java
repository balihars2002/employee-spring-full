package com.increff.employee.service;


import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.model.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.util.StringUtil;
@Service
public class ProductService {
    @Autowired
    private ProductDao prodao;
    @Autowired
    private BrandService brandService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(ProductForm f) throws ApiException {
        productnormalize(f);
        ProductPojo p= convert(f);
        if(StringUtil.isEmpty(p.getProBarcode())) {
            throw new ApiException("'Barcode' cannot be empty");
        }
        if(StringUtil.isEmpty(p.getProName())) {
            throw new ApiException("'Brand' cannot be empty");
        }
//        if(StringUtil.isEmpty(p.getMrp())) {
//            throw new ApiException("'Mrp' cannot be empty");
//        }
        //check duplicacy of brand name and category
//        if(getProCat(p.getBrand(),p.getCategory())==null){
//            throw new ApiException("Similar brand: " + p.getBrand() + " and category: " + p.getCategory() + " already existss" );
//        }
          prodao.insert(p);
    }

    @Transactional
    public void delete(int id) {
        prodao.delete(id);
    }
    @Transactional
    public void delete(String barcode) {
        prodao.delete(barcode);
    }

//    @Transactional(rollbackOn = ApiException.class)
//    public ProductPojo get(int id) throws ApiException{
//        return getCheck(id);
//    }
//
//    @Transactional(rollbackOn = ApiException.class)
//    public ProductPojo get(String barcode) throws ApiException{
//        return getCheck(barcode);
//    }
//
    @Transactional
    public List<ProductPojo> getAll() {
        return prodao.selectAll();
    }
//
//    @Transactional(rollbackOn  = ApiException.class)
//    public void update(int id, ProductPojo p) throws ApiException {
//        productnormalize(p);
////        ProductPojo ex = getCheck(id);
////        ex.setCategory(p.getCategory());
////        ex.setBrand(p.getBrand());
////        prodao.update(ex);
//    }
//    @Transactional
//    public ProductPojo getCheck(int id) throws ApiException {
//        ProductPojo p = prodao.select(id);
//        if (p == null) {
//            throw new ApiException("Product with given ID does not exit, id: " + id);
//        }
//        return p;
//    }
//    @Transactional
//    public ProductPojo getCheck(String barcode) throws ApiException {
//        ProductPojo p = prodao.select(barcode);
//        if (p == null) {
//            throw new ApiException("Product with given barcode does not exit, barcode: " + barcode);
//        }
//        return p;
//    }
//
    private ProductPojo convert(ProductForm form) throws ApiException{
        BrandPojo brandPojo= brandService.getBrandCat(form.getProBrand(),form.getProCategory());
        if(brandPojo==null){
            throw new ApiException("The brand and category does not exist");
        }
        int brandcat_id= brandPojo.getId();
        ProductPojo p= new ProductPojo(form.getProBarcode(),brandcat_id,form.getProName(),form.getProMrp());
        return p;
    }
    protected static void productnormalize(ProductPojo p) {
        p.setProBarcode(StringUtil.toLowerCase(p.getProBarcode()));
        p.setProName(StringUtil.toLowerCase(p.getProName()));
    }
    protected static void productnormalize(ProductForm f) {
        f.setProBarcode(StringUtil.toLowerCase(f.getProBarcode()));
        f.setProName(StringUtil.toLowerCase(f.getProName()));
    }
//    public ProductPojo getProCat(String brandName, String categoryName){
//        return prodao.select(brandName,categoryName);
//    }
//
////    String brandname= service.getBrand_from_id(p.getProId());
////    String categoryname= service.getCategory_from_id(p.getProCategory());
//
    public String getBrand_from_id(int brand_id) throws ApiException {
        BrandPojo brandpojo= brandService.GetbrandCatfromid(brand_id);
        if(brandpojo == null) {
            throw new ApiException("Brand Category does not exist.");
        }
        return brandpojo.getBrand();
    }
    public String getCategory_from_id(int brand_id) throws ApiException {
        BrandPojo brandpojo= brandService.GetbrandCatfromid(brand_id);
        if(brandpojo == null) {
            throw new ApiException("Brand Category does not exist.");
        }
        return brandpojo.getCategory();
    }
}
