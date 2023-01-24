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
    public void addInInv(@RequestBody InventoryForm inventoryForm) throws ApiException{
        inventoryDto.addDto(inventoryForm);
    }

    @ApiOperation(value = "Deletes a Product by Id")
    @RequestMapping(path = "/inventory/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) throws ApiException{
        inventoryDto.deleteInventoryById(id);
    }

    @ApiOperation(value = "Gets list of all Products in inventory")
    @RequestMapping(path = "/inventory",method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAllDto();
    }
    @ApiOperation(value = "Updates a Product quantity in inventory")
    @RequestMapping(path = "/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id,@RequestBody InventoryForm inventoryForm) throws ApiException {
       // ProductPojo p = convert(f);
        inventoryDto.updateInv(id,inventoryForm);
    }

//
    @ApiOperation(value = "Get a Product Quantity in Inventory by Id")
    @RequestMapping(path = "/inventory/{id}",method = RequestMethod.GET)

    public InventoryData get(@PathVariable Integer id) throws ApiException {
        return inventoryDto.getDataFromId(id);
    }
//    @ApiOperation(value = "Increase a Product Quantity")
//    @RequestMapping(path = "/product/increase{id}", method = RequestMethod.PUT)
//    public void increaseUpdate(@RequestParam(value="barcode") String barcode,@PathVariable int addQuantity) throws ApiException {
//        // ProductPojo p = convert(f);
//        inventoryDto.increaseQuantity(barcode,addQuantity);
//    }
//
}
