package com.increff.employee.controller.ui;

import com.increff.employee.api.ApiException;
import com.increff.employee.api.UserApi;
import com.increff.employee.dto.HelperDto;
import com.increff.employee.dto.UserDto;
import com.increff.employee.model.data.InfoData;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.UserPojo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Api
@RestController
public class SignupController extends AbstractUiController {

    @Autowired
    private UserDto userDto;
    @Autowired
    private InfoData info;


    @ApiOperation(value = "Initializes application")
    @RequestMapping(path = "/site/init", method = RequestMethod.GET)
    public ModelAndView showPage(UserForm form) throws ApiException {
        info.setMessage("");
        return mav("init.html");
    }

    @ApiOperation(value = "Registers User")
    @RequestMapping(path = "/site/init", method = RequestMethod.POST)
    public void initSite(@RequestBody UserForm form) throws ApiException {
        userDto.add(form);
    }




}
