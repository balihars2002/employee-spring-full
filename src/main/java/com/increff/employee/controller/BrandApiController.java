package com.increff.employee.controller;


import java.util.ArrayList;
import java.util.List;

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
public class BrandApiController {

    @Autowired
    private BrandService service;

    @ApiOperation(value = "Add a Brand")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException{
        BrandPojo p = convert(form);
        service.add(p);
    }


    @ApiOperation(value = "Deletes a Brand")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.DELETE)

    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @ApiOperation(value = "Get a Brand by ID")
    @RequestMapping(path = "/api/brand/{id}",method = RequestMethod.GET)

    public BrandData get(@PathVariable int id) throws ApiException {
        BrandPojo p= service.get(id);
        return convert(p);
    }

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "/api/brand",method = RequestMethod.GET)
    public List<BrandData> getAll() {
        List<BrandPojo> list = service.getAll();
        List<BrandData> list2 = new ArrayList<BrandData>();
        for(BrandPojo p : list){
            list2.add(convert(p));
        }
        return list2;
    }

    @ApiOperation(value = "Updates a Brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        BrandPojo p = convert(f);
        service.update(id, p);
    }

    private static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand()
        );
        d.setId(p.getId());
        return d;
    }
    private static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }
}
