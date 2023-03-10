package com.increff.employee.controller.ui;

import com.increff.employee.api.ApiException;
import com.increff.employee.dto.UserDto;
import com.increff.employee.model.data.InfoData;
import com.increff.employee.model.form.LoginForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api
@RestController
public class LoginController {

    @Autowired
    private UserDto userDto;
    @Autowired
    private InfoData info;

    @ApiOperation(value = "Logs in a user")
    @RequestMapping(path = "/session/login", method = RequestMethod.POST)
    public void login(HttpServletRequest req, @RequestBody LoginForm f) throws ApiException, NullPointerException {
        userDto.login(req, f);

    }

    @RequestMapping(path = "/session/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new ModelAndView("redirect:/site/logout");
    }


}
