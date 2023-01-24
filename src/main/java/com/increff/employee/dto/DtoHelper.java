package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;

public class DtoHelper {
    public static BrandData convertPojoToData(BrandPojo pojo) {
        BrandData d = new BrandData();
        d.setCategory(pojo.getCategory());
        d.setBrand(pojo.getBrand());
        d.setId(pojo.getId());
        return d;
    }
    public static BrandPojo convertFormToPojo(BrandForm form) {
        BrandPojo p = new BrandPojo();
        p.setCategory(form.getCategory());
        p.setBrand(form.getBrand());
        return p;
    }
    protected static void normalizeBrand(BrandPojo brandPojo) {
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
}
