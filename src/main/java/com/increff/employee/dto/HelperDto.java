package com.increff.employee.dto;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.*;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.InventoryApi;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class HelperDto {

    @Autowired
    private static InventoryApi inventoryApi;


    public static BrandData convertBrandPojoToData(BrandPojo pojo) {
        BrandData d = new BrandData();
        d.setCategory(pojo.getCategory());
        d.setBrand(pojo.getBrand());
        d.setId(pojo.getId());
        return d;
    }
    public static BrandPojo convertBrandFormToPojo(BrandForm form) {
        BrandPojo p = new BrandPojo();
        p.setCategory(form.getCategory());
        p.setBrand(form.getBrand());
        return p;
    }

    public static InventoryData convertInventoryPojoToData(InventoryPojo inventoryPojo) throws ApiException {
        InventoryData d = new InventoryData();
        d.setId(inventoryPojo.getId());
        d.setQuantity(inventoryPojo.getQuantity());
        return d;
    }

    public static void normalizeBrandPojo(BrandPojo brandPojo) {
        brandPojo.setBrand(StringUtil.toLowerCase(brandPojo.getBrand()));
        brandPojo.setCategory(StringUtil.toLowerCase((brandPojo.getCategory())));
    }

    public static void normalizeBrandForm(BrandForm form) {
        form.setBrand(StringUtil.toLowerCase(form.getBrand()));
        form.setCategory(StringUtil.toLowerCase((form.getCategory())));
    }


    public static void normalizeProductForm(ProductForm form) {
        form.setName(StringUtil.toLowerCase(form.getName()));
        form.setBrand(StringUtil.toLowerCase(form.getBrand()));
        form.setCategory(StringUtil.toLowerCase(form.getCategory()));
    }
    public static void validateProductForm(ProductForm form) throws ApiException{
        if(StringUtil.isEmpty(form.getBarcode())){
            throw new ApiException("Barcode cannot be Empty!");
        }
        if(StringUtil.isEmpty(form.getName())){
            throw new ApiException("Name cannot be Empty!");
        }
        if(StringUtil.isEmpty(form.getBrand())){
            throw new ApiException("Brand cannot be Empty!");
        }
        if(StringUtil.isEmpty(form.getCategory())){
            throw new ApiException("Category cannot be Empty!");
        }
        if(form.getMrp() == null || form.getMrp() <= 0){
            throw new ApiException("Mrp cannot be empty,negative or 0");
        }
    }
    public static void validateProductEditForm(ProductForm form) throws ApiException{
        if(StringUtil.isEmpty(form.getBarcode())){
            throw new ApiException("Barcode cannot be Empty!");
        }
        if(StringUtil.isEmpty(form.getName())){
            throw new ApiException("Name cannot be Empty!");
        }
        if(form.getMrp() == null || form.getMrp() <= 0){
            throw new ApiException("Mrp cannot be empty,negative or 0");
        }
    }

    public static ProductData convertProductPojoToData(ProductPojo productPojo) throws ApiException {
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setBarcode(productPojo.getBarcode());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
        return productData;
    }

    public static OrderItemPojo convertOrderItemFormToPojo(OrderItemForm orderItemForm, Integer id) throws ApiException{
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(id);
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        return orderItemPojo;
    }

    public static OrderItemData convertOrderItemPojoToData(OrderItemPojo orderItemPojo) throws ApiException {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        orderItemData.setProductId(orderItemPojo.getProductId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        return orderItemData;

    }


}
