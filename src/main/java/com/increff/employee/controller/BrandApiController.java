package com.increff.employee.controller;


import java.util.ArrayList;
import java.util.List;

import com.increff.employee.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
// Todo : Remove api word from all the controllers
public class BrandApiController {
    @Autowired
    private BrandDto branddto;
    @ApiOperation(value = "Add a Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException{
        branddto.add(form);
    }

    @ApiOperation(value = "Deletes a Brand")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.DELETE)

    public void delete(@PathVariable int id) {
        branddto.delete(id);
    }

    @ApiOperation(value = "Get a Brand by ID")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.GET)

    public BrandData get(@PathVariable int id) throws ApiException {
        return branddto.get(id);
    }

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/api/brand",method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return branddto.getAlllist();
    }

    @ApiOperation(value = "Updates a Brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        branddto.updatelist(id,f);
    }


}
