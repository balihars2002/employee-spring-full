package com.increff.employee.controller.operator;

import com.increff.employee.dto.InventoryDto;
import com.increff.employee.model.data.InventoryData;
import com.increff.employee.model.form.InventoryForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api
@RestController
@RequestMapping("/api/operator/inventory")
public class OperatorInventoryController {
    @Autowired
    private InventoryDto inventoryDto;


    @ApiOperation(value = "Gets list of all Products in inventory")
    @RequestMapping(path = "",method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return inventoryDto.getAllDto();
    }

    @ApiOperation(value = "Get a Product Quantity in Inventory by Id")
    @RequestMapping(path = "/inventory/{id}",method = RequestMethod.GET)

    public InventoryData get(@PathVariable Integer id) throws ApiException {
        return inventoryDto.getDataFromId(id);
    }
}
