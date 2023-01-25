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
import com.increff.employee.service.BrandApi;
import com.increff.employee.service.InventoryApi;
import com.increff.employee.service.ProductApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.util.StringUtil;
@Service
public class ProductDto extends DtoHelper{
    @Autowired
    private ProductApi productApi;
    @Autowired
    private BrandApi brandApi;
    @Autowired
    private InventoryApi inventoryApi;

    @Transactional(rollbackOn = ApiException.class)
    public void addDto(ProductForm productForm) throws ApiException {
        normaliseProduct(productForm);
        ProductPojo productPojo= convertFormToPojo(productForm);
        if(StringUtil.isEmpty(productPojo.getBarcode())) {
            throw new ApiException("'Barcode' cannot be empty");
        }
        if(StringUtil.isEmpty(productPojo.getName())) {
            throw new ApiException("'Name' cannot be empty");
        }
        checkMrp(productPojo.getMrp());
        productApi.insertService(productPojo);

        InventoryPojo inventoryPojo= new InventoryPojo();
        inventoryPojo.setQuantity(0);
        inventoryPojo.setProductId(productPojo.getId());
        inventoryApi.addService(inventoryPojo);
    }
    @Transactional
    public ProductPojo getCheckFromService(String barcode) throws ApiException{
        return productApi.getCheckByBarcode(barcode);
    }
    @Transactional
    public void deletedToBarcode(Integer id) throws ApiException {
        ProductPojo productPojo = productApi.getPojoFromId(id);
        inventoryApi.deleteService(productPojo.getId());
        productApi.deleteServiceById(id);
    }


    @Transactional
    public List<ProductData> getAllDto() throws ApiException {
        List<ProductPojo> list= productApi.selectAllService();
        List<ProductData> list1 = new ArrayList<ProductData>();
        for(ProductPojo p:list){
            list1.add(convertPojoToData(p));
        }
        return list1;
    }
    @Transactional(rollbackOn  = ApiException.class)
    public void updateProduct(Integer id, ProductForm productForm) throws ApiException {
       // ProductPojo productPojo= convertFormToPojo(productForm);
        ProductPojo productPojo1 = productApi.getPojoFromId(id);
        if(productPojo1 == null)
        {
            throw new ApiException("The product with given id does not exist.");
        }
        //String barcode= productPojo1.getProBarcode();
       // normalizeProduct(productPojo);
        normalizeProduct(productPojo1);
     //   ProductPojo productPojo2 = productService.getPojoFromId(productPojo1.getProId());
        productPojo1.setBarcode(productForm.getBarcode());
        productPojo1.setMrp(productForm.getMrp());
        productPojo1.setName(productForm.getName());
        productApi.update(productPojo1);

    }
    public void checkMrp(Double mrp) throws ApiException{
        if(mrp<=0){
            throw new ApiException("Mrp can only be in fraction and positive!");
        }
    }
    public Integer getQuantityFromInventoryByPID(Integer id){
       Integer quantity = inventoryApi.getPojoFromId(id).getQuantity();
       return quantity;
    }
    public ProductData getDataFromBarcode(String barcode) throws ApiException {
          ProductPojo productPojo = productApi.getPojoFromBarcode(barcode);
          if(productPojo == null){
              throw new ApiException("Product with given barcode does not exist.");
          }
          return convertPojoToData(productPojo);
    }
    public ProductData getDataFromId(Integer id) throws ApiException {
        ProductPojo productPojo = productApi.getPojoFromId(id);
        if(productPojo == null){
            throw new ApiException("Product with given barcode does not exist.");
        }
        return convertPojoToData(productPojo);
    }

    private ProductPojo convertFormToPojo(ProductForm form) throws ApiException{
        BrandPojo brandPojo= brandApi.getBrandCat(form.getBrand(),form.getCategory());
        if(brandPojo==null){
            throw new ApiException("The brand and category does not exist");
        }
        Integer brandCategory_Id= brandPojo.getId();
        ProductPojo productPojo= new ProductPojo(form.getBarcode(),brandCategory_Id,form.getName(),form.getMrp());
        return productPojo;
    }

    private ProductData convertPojoToData(ProductPojo productPojo) throws ApiException {
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setBarcode(productPojo.getBarcode());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
        BrandPojo brandPojo= brandApi.getBrandCatFromiId(productPojo.getBrand_category());
        if(brandPojo == null){
            throw new ApiException("ID is not valid");
        }
        String brandName= brandPojo.getBrand();
        String categoryName= brandPojo.getCategory();
        productData.setBrand(brandName);
        productData.setCategory(categoryName);
        return productData;
    }
}
