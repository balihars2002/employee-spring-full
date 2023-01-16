package com.increff.employee.controller;


import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
public class ProductApiController {
    @Autowired
    private  ProductService service;
    @ApiOperation(value = "Add a Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException{
//        ProductPojo p= convert(form);
//        ProductPojo d= service.select(p.getProBrand(),getProCat());
        service.add(form);
    }

    @ApiOperation(value = "Deletes a Product by Barcode")
    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.DELETE)

    public void delete(@PathVariable String barcode) {
        service.delete(barcode);
    }

//    @ApiOperation(value = "Deletes a Product by ID")
//    @RequestMapping(path = "/api/product/{id}",method = RequestMethod.DELETE)
//
//    public void delete(@PathVariable int id) {
//        service.delete(id);
//    }

    @ApiOperation(value = "Get a Product by Id")
    @RequestMapping(path = "/api/product/{id}",method = RequestMethod.GET)

    public ProductData get(@PathVariable int id) throws ApiException {
        ProductPojo p= service.get(id);
        return convert(p);
    }
//
//    @ApiOperation(value = "Get a Product by Barcode")
//    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.GET)
//
//    public ProductData get(@PathVariable String barcode) throws ApiException {
//        ProductPojo p= service.get(barcode);
//        return convert(p);
//    }
    @ApiOperation(value = "Gets list of all Products")
    @RequestMapping(path = "/api/product",method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = service.getAll();
        List<ProductData> list2 = new ArrayList<ProductData>();
        for(ProductPojo p : list){
            list2.add(convert(p));
       }
        return list2;
    }

//    @ApiOperation(value = "Updates a Product")
//    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
//    public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
//        ProductPojo p = convert(f);
//        service.update(id, p);
//    }












    private ProductData convert(ProductPojo p) throws ApiException {
        ProductData d = new ProductData();
        d.setProBarcode(p.getProBarcode());
        d.setProName(p.getProName());
        d.setProMrp(p.getProMrp());
        String brandname= service.getBrand_from_id(p.getProId());
        String categoryname= service.getCategory_from_id(p.getProId());
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
