package com.increff.employee.controller;

import java.util.List;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
@RequestMapping("/api")
public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;
    @ApiOperation(value = "Add a Product in Inventory")
    @RequestMapping(path = "/inventory",method = RequestMethod.POST)
    public void addinInv(@RequestBody InventoryForm f) throws ApiException{
        inventoryDto.addDto(f);
    }

    @ApiOperation(value = "Deletes a Product by Barcode")
    @RequestMapping(path = "/product/{barcode}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String barcode) throws ApiException{
        inventoryDto.deleteInventoryByBarcode(barcode);
    }

    @ApiOperation(value = "Gets list of all Products")
    @RequestMapping(path = "/inventory",method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAllDto();
    }
    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/product/{id}", method = RequestMethod.PUT)
    public void update(@RequestParam(value="barcode") String barcode,@RequestBody InventoryForm inventoryForm) throws ApiException {
       // ProductPojo p = convert(f);
        inventoryDto.updateInv(barcode,inventoryForm);
    }

//    @ApiOperation(value = "Updates a Product")
//    @RequestMapping(path = "/product/{id}", method = RequestMethod.PUT)
//    public void update(@PathVariable String barcode,@PathVariable int quanTity) throws ApiException {
//        // ProductPojo p = convert(f);
//        inventoryDto.updateInv(barcode,quanTity);
//    }
}
