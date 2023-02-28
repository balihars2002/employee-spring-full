package com.increff.employee.controller.supervisor;


import com.increff.employee.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.increff.employee.model.form.ProductForm;
import com.increff.employee.api.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Add a Product")
    @RequestMapping(path = "/tsv", method = RequestMethod.POST)
    public void add(@RequestBody List<ProductForm> formList) throws ApiException{
        for(ProductForm form : formList) {
            productDto.add(form);
        }
    }
    @ApiOperation(value = "Add a Product")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException{
        productDto.add(form);
    }

    @ApiOperation(value = "Deletes a Product by ID")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) throws ApiException {
        productDto.delete(id);
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody ProductForm productForm) throws ApiException {
        productDto.update(id,productForm);
    }



}
