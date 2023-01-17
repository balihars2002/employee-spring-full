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
//    @Autowired
//    private BrandService brandService;

    @Transactional(rollbackOn = ApiException.class)
    public void insertservice(ProductPojo p) throws ApiException {
          prodao.insert(p);
    }

    @Transactional
    public void deleteserviceid(int id) {
        prodao.delete(id);
    }
    @Transactional
    public void deleteservicebarcode(String barcode) {
        prodao.delete(barcode);
    }

//    @Transactional(rollbackOn = ApiException.class)
//    public ProductPojo getid(int id) throws ApiException{
//        return getCheckid(id);
//    }
//
//    @Transactional(rollbackOn = ApiException.class)
//    public ProductPojo getbarcode(String barcode) throws ApiException{
//        return getCheckstring(barcode);
//    }

    @Transactional
    public List<ProductPojo> selectAllservice() {
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
      public ProductPojo givepojobyid(int id){
            return prodao.selectpojobyid(id);
      }
        public ProductPojo givepojobybarcode(String barcode){
            return prodao.selectpojobybarcode(barcode);
        }
        public ProductPojo getCheckbybarcode(String barcode) throws ApiException{
            ProductPojo p = prodao.selectpojobybarcode(barcode);
            if(p == null){
                throw new ApiException("The product with given barcode does not exist");
            }
            return p;
        }
//    @Transactional
//    public ProductPojo getCheckid(int id) throws ApiException {
//        ProductPojo p = prodao.selectpojobyid(id);
//        if (p == null) {
//            throw new ApiException("Product with given ID does not exit, id: " + id);
//        }
//        return p;
//    }
//    @Transactional
//    public ProductPojo getCheckstring(String barcode) throws ApiException {
//        ProductPojo p = prodao.selectpojobybarcode(barcode);
//        if (p == null) {
//            throw new ApiException("Product with given barcode does not exit, barcode: " + barcode);
//        }
//        return p;
//    }

//    private ProductPojo convert(ProductForm form) throws ApiException{
//        BrandPojo brandPojo= brandService.getBrandCat(form.getProBrand(),form.getProCategory());
//        if(brandPojo==null){
//            throw new ApiException("The brand and category does not exist");
//        }
//        int brandcat_id= brandPojo.getId();
//        ProductPojo p= new ProductPojo(form.getProBarcode(),brandcat_id,form.getProName(),form.getProMrp());
//        return p;
//    }
//    protected static void productnormalize(ProductPojo p) {
//        p.setProBarcode(StringUtil.toLowerCase(p.getProBarcode()));
//        p.setProName(StringUtil.toLowerCase(p.getProName()));
//    }
//    protected static void productnormalize(ProductForm f) {
//        f.setProBarcode(StringUtil.toLowerCase(f.getProBarcode()));
//        f.setProName(StringUtil.toLowerCase(f.getProName()));
//    }
//    public ProductPojo getProCat(String brandName, String categoryName){
//        return prodao.select(brandName,categoryName);
//    }
//
////    String brandname= service.getBrand_from_id(p.getProId());
////    String categoryname= service.getCategory_from_id(p.getProCategory());
//
//    public String getBrand_from_id(int brand_id) throws ApiException {
////        BrandPojo brandpojo= brandService.GetbrandCatfromid(brand_id);
////        if(brandpojo == null) {
////            throw new ApiException("Brand Category does not exist from brand.");
////        }
//        return brandService.GetbrandCatfromid(brand_id).getBrand();
//    }
//    public String getCategory_from_id(int brand_id) throws ApiException {
////        BrandPojo brandpojo= brandService.GetbrandCatfromid(brand_id);
////        if(brandpojo == null) {
////            throw new ApiException("Brand Category does not exist from category.");
////        }
//        return brandService.GetbrandCatfromid(brand_id).getCategory();
//    }
}
