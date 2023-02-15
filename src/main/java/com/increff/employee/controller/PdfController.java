//package com.increff.employee.controller;
//
//import com.increff.employee.dto.PdfDto;
//import com.increff.employee.model.form.OrderForm;
//import com.increff.employee.service.ApiException;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@Api
//@RestController
//@RequestMapping
//public class PdfController {
//
//    @Autowired
//    private PdfDto pdfDto;
//
//    @ApiOperation(value = "Download Invoice for an Order")
//    @RequestMapping(path = "", method = RequestMethod.POST)
//    public void generatePdf(@PathVariable Integer id) throws ApiException {
//        pdfDto.generatePdf(id);
//    }
//}
