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
    protected static void normalizeBrand(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase((p.getCategory())));
    }

    protected static void normalizeProduct(ProductPojo p) {
        p.setProBarcode(StringUtil.toLowerCase(p.getProBarcode()));
        p.setProName(StringUtil.toLowerCase(p.getProName()));
    }
    protected static void normaliseProduct(ProductForm f) {
        f.setProBarcode(StringUtil.toLowerCase(f.getProBarcode()));
        f.setProName(StringUtil.toLowerCase(f.getProName()));
    }
}
