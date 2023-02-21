package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.flowApi.ProductFlowAPi;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandApi;
import com.increff.employee.service.InventoryApi;
import com.increff.employee.service.ProductApi;
import com.increff.employee.spring.SecurityConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDto extends HelperDto {
    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private InventoryApi inventoryApi;
    @Autowired
    private ProductFlowAPi productFlowAPi;

    private final static Logger logger = Logger.getLogger(SecurityConfig.class);

    public void addDto(ProductForm productForm) throws ApiException {
        validateProductForm(productForm);
        normalizeProductForm(productForm);
        productApi.getPojoByBarcode(productForm.getBarcode());
        ProductPojo productPojo= convertFormToPojo(productForm);
        productApi.getCheck(productPojo.getBrand_category(),productPojo.getName());
        productFlowAPi.insert(productPojo);
    }
//    public ProductPojo getCheckFromService(String barcode) throws ApiException{
//        return productApi.getPojoFromBarcode(barcode);
//    }

    public void delete(Integer id) throws ApiException {
        ProductPojo productPojo = productApi.getPojoFromId(id);
        inventoryApi.deleteService(productPojo.getId());
        productApi.deleteServiceById(id);
    }

    public List<ProductData> getAllDto() throws ApiException {
        List<ProductPojo> list= productApi.selectAllService();
        List<ProductData> list1 = new ArrayList<ProductData>();
        for(ProductPojo productPojo:list){
            ProductData productData = convertProductPojoToData(productPojo);
            BrandPojo brandPojo= brandApi.getCheck(productPojo.getBrand_category());
            String brandName= brandPojo.getBrand();
            String categoryName= brandPojo.getCategory();
            productData.setBrand(brandName);
            productData.setCategory(categoryName);
            list1.add(productData);
        }
        return list1;
    }
    public void updateProduct(Integer id, ProductForm productForm) throws ApiException {
        validateProductEditForm(productForm);
        ProductPojo productPojo1 = productApi.getPojoFromId(id);
        productPojo1.setBarcode(productForm.getBarcode());
        productPojo1.setMrp(productForm.getMrp());
        productPojo1.setName(productForm.getName());
        normalizeProductPojo(productPojo1);

        productApi.update(productPojo1);

    }

    public Integer getQuantityFromInventoryByPID(Integer id) throws ApiException {
       return inventoryApi.getPojoFromId(id).getQuantity();
    }
    public ProductData getDataFromBarcode(String barcode) throws ApiException {
          ProductPojo pojo = productApi.getPojoFromBarcode(barcode);
          return convertProductPojoToData(pojo);
    }
    public ProductData getDataFromId(Integer id) throws ApiException {
        ProductPojo productPojo = productApi.getPojoFromId(id);
        return convertProductPojoToData(productPojo);
    }

    private ProductPojo convertFormToPojo(ProductForm form) throws ApiException{
        BrandPojo brandPojo = brandApi.getBrandCat(form.getBrand(),form.getCategory());
        Integer brandCategory_Id = brandPojo.getId();
        ProductPojo productPojo = new ProductPojo(form.getBarcode(),brandCategory_Id,form.getName(),form.getMrp());
        return productPojo;
    }


}
