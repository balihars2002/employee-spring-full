package com.increff.employee.controller.operator;

import com.increff.employee.dto.BrandDto;
import com.increff.employee.model.data.BrandData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/operator/brand")
public class OperatorBrandController {

    @Autowired
    private BrandDto brandDto;


    @ApiOperation(value = "Get a Brand by ID")
    @RequestMapping(path = "/{id}",method = RequestMethod.GET)

    public BrandData get(@PathVariable Integer id) throws ApiException {
        return brandDto.get(id);
    }

    @ApiOperation(value = "Gets list of all Brands")
    @RequestMapping(path = "",method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return brandDto.getAllList();
    }

}
