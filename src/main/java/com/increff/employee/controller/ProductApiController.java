package com.increff.employee.controller;


import java.util.ArrayList;
import java.util.List;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private ProductDto productDto;
    @Autowired
    private InventoryDto inventoryDto;
    @ApiOperation(value = "Add a Product")
    @RequestMapping(path = "/api/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException{
//        ProductPojo p= convert(form);
//        ProductPojo d= service.select(p.getProBrand(),getProCat());
        productDto.adddto(form);
    }

    @ApiOperation(value = "Deletes a Product by Barcode")
    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String barcode) {
        productDto.deletedtobarcode(barcode);
    }

//    @ApiOperation(value = "Deletes a Product by ID")
//    @RequestMapping(path = "/api/product/{id}",method = RequestMethod.DELETE)
//
//    public void delete(@PathVariable int id) {
//        service.delete(id);
//    }

//    @ApiOperation(value = "Get a Product by Id")
//    @RequestMapping(path = "/api/product/{id}",method = RequestMethod.GET)
//
//    public ProductData get(@PathVariable int id) throws ApiException {
//        ProductPojo p= service.getid(id);
//        return convert(p);
//    }
//
//    @ApiOperation(value = "Get a Product by Barcode")
//    @RequestMapping(path = "/api/product/",method = RequestMethod.GET)
//
//    public ProductData get(@RequestParam(value="barcode") String barcode) throws ApiException {
//        ProductPojo p= service.getbarcode(barcode);
//        return convert(p);
//    }
    @ApiOperation(value = "Gets list of all Products")
    @RequestMapping(path = "/api/product",method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return productDto.getAlldto();
    }

//    @ApiOperation(value = "Updates a Product")
//    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
//    public void update(@RequestParam(value="barcode") String barcode, @RequestBody ProductForm f) throws ApiException {
//        ProductPojo p = convert(f);
//        service.update(id, p);
//        delete(barcode);
//        add(f);
//        productDto.updateproduct(barcode,f);
//    }













}
