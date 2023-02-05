package com.increff.employee.dto;

import com.increff.employee.model.data.BrandData;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.data.OrderItemData;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.model.form.OrderItemForm;
import com.increff.employee.model.form.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandApi;
import com.increff.employee.service.InventoryApi;
import com.increff.employee.service.ProductApi;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class DtoHelper {

    @Autowired
    private static InventoryApi inventoryApi;

    @Autowired
    private static BrandApi brandApi;

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
        ProductApi productApi = null;
        ProductPojo productPojo= productApi.givePojoById(inventoryPojo.getProductId());
        //  get brand and category from id
        BrandApi brandApi = null;
        BrandPojo brandPojo = brandApi.getCheck(productPojo.getBrand_category());
        d.setId(inventoryPojo.getId());
        d.setProductId(productPojo.getId());
        d.setQuantity(inventoryPojo.getQuantity());
        d.setMrp(productPojo.getMrp());
        d.setName(productPojo.getName());
        d.setBarcode(productPojo.getBarcode());
        d.setBrand(brandPojo.getBrand());
        d.setCategory(brandPojo.getCategory());
        return d;
    }
    public static InventoryPojo getUpdatedInventoryPojo(Integer id,Integer changeQuantityBy,Boolean increase){
        if(increase) {
            changeQuantityBy = -changeQuantityBy;
        }
        InventoryPojo inventoryPojo = inventoryApi.getPojoFromProductId(id);
        int initialQuantity = inventoryPojo.getQuantity();
        inventoryPojo.setQuantity(initialQuantity - changeQuantityBy);
        return inventoryPojo;
    }

    public static void normalizeBrand(BrandPojo brandPojo) {
        brandPojo.setBrand(StringUtil.toLowerCase(brandPojo.getBrand()));
        brandPojo.setCategory(StringUtil.toLowerCase((brandPojo.getCategory())));
    }

    protected static void normalizeProduct(ProductPojo productPojo) {
        productPojo.setBarcode(StringUtil.toLowerCase(productPojo.getBarcode()));
        productPojo.setName(StringUtil.toLowerCase(productPojo.getName()));
    }
    protected static void normaliseProduct(ProductForm productForm) {
        productForm.setBarcode(StringUtil.toLowerCase(productForm.getBarcode()));
        productForm.setName(StringUtil.toLowerCase(productForm.getName()));
    }

    public static ProductData convertProductPojoToData(ProductPojo productPojo) throws ApiException {
        ProductData productData = new ProductData();
        productData.setId(productPojo.getId());
        productData.setBarcode(productPojo.getBarcode());
        productData.setName(productPojo.getName());
        productData.setMrp(productPojo.getMrp());
//        BrandPojo brandPojo= brandApi.getBrandCatFromId(productPojo.getBrand_category());
//        if(brandPojo == null){
//            throw new ApiException("ID is not valid");
//        }
//        String brandName= brandPojo.getBrand();
//        String categoryName= brandPojo.getCategory();
//        productData.setBrand(brandName);
//        productData.setCategory(categoryName);
        return productData;
    }

    public static OrderItemPojo convertOrderItemFormToPojo(OrderItemForm orderItemForm, Integer id) throws ApiException{
//        ProductPojo productPojo = productApi.getPojoFromBarcode(orderItemForm.getBarcode());
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(id);
//        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
//        orderItemPojo.setSellingPrice(productPojo.getMrp());
        return orderItemPojo;
    }

    public static OrderItemData convertOrderItemPojoToData(OrderItemPojo orderItemPojo) throws ApiException {
        OrderItemData orderItemData = new OrderItemData();
        // orderItemData.setId(orderItemPojo.getId());
        orderItemData.setId(orderItemPojo.getId());
//        ProductPojo productPojo = productApi.getPojoFromId(orderItemPojo.getProductId());
//        orderItemData.setBarcode(productPojo.getBarcode());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setSellingPrice(orderItemPojo.getSellingPrice());
        orderItemData.setProductId(orderItemPojo.getProductId());
        orderItemData.setOrderId(orderItemPojo.getOrderId());
        return orderItemData;

    }
}
