package com.increff.employee.controller.operator;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.data.ProductData;
import com.increff.employee.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/operator/product")
public class OperatorProductController {
    @Autowired
    private ProductDto productDto;

    @ApiOperation(value = "Get a Product by Id")
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)

    public ProductData get(@PathVariable Integer id) throws ApiException {
        return productDto.getDataFromId(id);
    }

    @ApiOperation(value = "Gets list of all Products")
    @RequestMapping(path = "",method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return productDto.getAll();
    }

}
