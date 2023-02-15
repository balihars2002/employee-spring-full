package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.increff.employee.model.data.InfoData;
import com.increff.employee.model.form.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserApi;

import io.swagger.annotations.ApiOperation;


@Controller
public class InitController extends AbstractUiController {

	@Autowired
	private UserApi service;
	@Autowired
	private InfoData info;

//	@Value("${app.supervisors}")
//	private String email;

	@Value("#{'${app.supervisors}'.split(',')}")
	private List<String> emails;


	@ApiOperation(value = "Initializes application")
	@RequestMapping(path = "/site/init", method = RequestMethod.GET)
	public ModelAndView showPage(UserForm form) throws ApiException {
		info.setMessage("");
		return mav("init.html");
	}

	@ApiOperation(value = "Registers User")
	@RequestMapping(path = "/site/init", method = RequestMethod.POST)
	public ModelAndView initSite(UserForm form) throws ApiException {
		boolean flag = false;
		for(String email:emails) {
			System.out.println(email);
			if (email.equals(form.getEmail())) {
				form.setRole("supervisor");
				flag = true;
			}
		}
		if(!flag){
			form.setRole("operator");
		}
		UserPojo p = convert(form);
			service.add(p);
			info.setMessage("Application initialized");
//		}
		return mav("init.html");

	}

	private static UserPojo convert(UserForm f) {
		UserPojo p = new UserPojo();
		p.setEmail(f.getEmail());
		p.setRole(f.getRole());
		p.setPassword(f.getPassword());
		return p;
	}

}
