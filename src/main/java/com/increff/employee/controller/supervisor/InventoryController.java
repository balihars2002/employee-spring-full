package com.increff.employee.controller.supervisor;

import java.util.List;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Add a Product in Inventory")
    @RequestMapping(path = "",method = RequestMethod.POST)
    public void addInInv(@RequestBody InventoryForm inventoryForm) throws ApiException{
        inventoryDto.addDto(inventoryForm);
    }

    @ApiOperation(value = "Deletes a Product by Id")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) throws ApiException{
        inventoryDto.deleteInventoryById(id);
    }


    @ApiOperation(value = "Updates a Product quantity in inventory")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Integer id,@RequestBody InventoryForm inventoryForm) throws ApiException {
       // ProductPojo p = convert(f);
        inventoryDto.updateInv(id,inventoryForm);
    }

//

}
