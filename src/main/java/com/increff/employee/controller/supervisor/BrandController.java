package com.increff.employee.controller.supervisor;


import com.increff.employee.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.form.BrandForm;
import com.increff.employee.api.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/")

public class BrandController {
    @Autowired
    private BrandDto brandDto;

    @ApiOperation(value = "Add a Brand")
    @RequestMapping(path = "/brandTsv", method = RequestMethod.POST)
    public List<List<String>> addList(@RequestBody List<BrandForm> formList) throws ApiException{
       return brandDto.addList(formList);
    }

    @ApiOperation(value = "Add a Brand")
    @RequestMapping(path = "/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException{
        brandDto.add(form);
    }

    @ApiOperation(value = "Updates a Brand")
    @RequestMapping(path = "/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id, @RequestBody BrandForm form) throws ApiException {
        brandDto.updateList(id,form);
    }
}
