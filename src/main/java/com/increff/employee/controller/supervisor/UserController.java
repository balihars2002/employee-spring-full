package com.increff.employee.controller.supervisor;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.dto.UserDto;
import com.increff.employee.model.form.BrandForm;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.data.UserData;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.api.ApiException;
import com.increff.employee.api.UserApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class UserController {

	@Autowired
	private UserDto userDto;

	@ApiOperation(value = "Adds a user")
	@RequestMapping(path = "/api/user", method = RequestMethod.POST)
	public void addUser(@RequestBody UserForm form) throws ApiException {
		userDto.add(form);
	}



	@ApiOperation(value = "Gets list of all users")
	@RequestMapping(path = "/api/user", method = RequestMethod.GET)
	public List<UserData> getAllUser() {
		return userDto.getAll();
	}

	@ApiOperation(value = "Updates a User")
	@RequestMapping(path = "/api/user/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable Integer id) throws ApiException {
		userDto.update(id);
	}

}
