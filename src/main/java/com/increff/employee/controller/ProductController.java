package com.increff.employee.controller;


import java.util.List;

import com.increff.employee.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductDto productDto;
    @ApiOperation(value = "Add a Product")
    @RequestMapping(path = "/product", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException{
        productDto.addDto(form);
    }

    @ApiOperation(value = "Deletes a Product by ID")
    @RequestMapping(path = "/product/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) throws ApiException {
        productDto.deletedToBarcode(id);
    }
//

    @ApiOperation(value = "Get a Product by Id")
    @RequestMapping(path = "/product/{id}",method = RequestMethod.GET)

    public ProductData get(@PathVariable Integer id) throws ApiException {
        return productDto.getDataFromId(id);
    }
//
//    @ApiOperation(value = "Get a Product by Barcode")
//    @RequestMapping(path = "/product/{barcode}",method = RequestMethod.GET)
//
//    public ProductData get(@PathVariable String barcode) throws ApiException {
//        return productDto.getDataFromBarcode(barcode);
//    }
    @ApiOperation(value = "Gets list of all Products")
    @RequestMapping(path = "/product",method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return productDto.getAllDto();
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody ProductForm productForm) throws ApiException {
        productDto.updateProduct(id,productForm);
    }



}
