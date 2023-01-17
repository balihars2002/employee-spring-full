package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.*;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;
//    @ApiOperation(value = "Add a Product in Inventory")
//    @RequestMapping(path = "/api/inventory",method = RequestMethod.POST)
//    public void addinInv(@RequestBody InventoryForm f) throws ApiException{
//        inventoryDto.addto(f);
//    }

//    @ApiOperation(value = "Deletes a Product by Barcode")
//    @RequestMapping(path = "/api/product/{barcode}",method = RequestMethod.DELETE)
//    public void delete(@PathVariable String barcode) {
//        inventoryDto.deletedtobarcode(barcode);
//    }
     @ApiOperation(value = "Gets list of all Products")
    @RequestMapping(path = "/api/inventory",method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAllDto();
    }
    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable String barcode) throws ApiException {
       // ProductPojo p = convert(f);
        inventoryDto.updateinv(barcode);
    }

}
