package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.StringUtil;

public class DtoHelper {
    public static BrandData convert(BrandPojo pojo) {
        BrandData d = new BrandData();
        d.setCategory(pojo.getCategory());
        d.setBrand(pojo.getBrand());
        d.setId(pojo.getId());
        return d;
    }
    public static BrandPojo convert(BrandForm form) {
        BrandPojo p = new BrandPojo();
        p.setCategory(form.getCategory());
        p.setBrand(form.getBrand());
        return p;
    }
    protected static void normalizeBrand(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase((p.getCategory())));
    }
}
