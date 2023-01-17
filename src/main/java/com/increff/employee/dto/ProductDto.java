package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.model.ProductData;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.util.StringUtil;
@Service
public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private InventoryService inventoryService;

    @Transactional(rollbackOn = ApiException.class)
    public void adddto(ProductForm f) throws ApiException {
        productnormalize(f);
        ProductPojo p= convertformtopojo(f);
        if(StringUtil.isEmpty(p.getProBarcode())) {
            throw new ApiException("'Barcode' cannot be empty");
        }
        if(StringUtil.isEmpty(p.getProName())) {
            throw new ApiException("'Brand' cannot be empty");
        }
        productService.insertservice(p);

        InventoryPojo ip= new InventoryPojo(p.getProId(),0);
        inventoryService.addservice(ip);
    }
    @Transactional
    public ProductPojo getCheckfromservice(String barcode) throws ApiException{
        return productService.getCheckbybarcode(barcode);
    }
    @Transactional
    public void deletedtobarcode(String barcode) {
        productService.deleteservicebarcode(barcode);
    }

    @Transactional
    public List<ProductData> getAlldto() throws ApiException {
        List<ProductPojo> list= productService.selectAllservice();
        List<ProductData> list1 = new ArrayList<ProductData>();
        for(ProductPojo p:list){
            list1.add(convertpojotodata(p));
        }
        return list1;
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void updateproduct(String barcode, ProductForm f) throws ApiException {
//          deletedtobarcode(barcode);
//          adddto(f);
//        ProductPojo productPojo= convertformtopojo(f);
//        productnormalize(productPojo);
//        ProductPojo ex = getCheckfromservice(barcode);
//        ex.setProBarcode(productPojo.getProBarcode());
//        ex.setProMrp(productPojo.getProMrp());
//        ex.setProName(productPojo.getProName());
//        brandService.update(ex);

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

    //    @Transactional(rollbackOn  = ApiException.class)
//    public void update(int id, ProductPojo p) throws ApiException {
//        productnormalize(p);
////        ProductPojo ex = getCheck(id);
////        ex.setCategory(p.getCategory());
////        ex.setBrand(p.getBrand());
////        prodao.update(ex);
//    }
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
    private ProductPojo convertformtopojo(ProductForm form) throws ApiException{
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
    private ProductData convertpojotodata(ProductPojo p) throws ApiException {
        ProductData d = new ProductData();
        d.setProId(p.getProId());
        d.setProBarcode(p.getProBarcode());
        d.setProName(p.getProName());
        d.setProMrp(p.getProMrp());
        BrandPojo bp= brandService.getBrandCatFromiId(p.getProbrandCategory());
        if(bp == null){
            throw new ApiException("ID is not valid");
        }
        String brandname= bp.getBrand();
        String categoryname= bp.getCategory();
        d.setProBrand(brandname);
        d.setProCategory(categoryname);
        return d;
    }
//    private static ProductPojo convert(ProductForm f) {
//        ProductPojo p = new ProductPojo();
//        p.setProCategory(f.getProCategory());
//       // p.setProBrand(f.getProBrand());
//        return p;
//    }
}
