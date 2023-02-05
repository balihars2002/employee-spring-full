package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return mav("product.html");
	}

	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}

	@RequestMapping(value = "/ui/order")
	public ModelAndView order() {
		return mav("order.html");
	}

	@RequestMapping(value = "/ui/orderItem/{id}")
	public ModelAndView orderItem(@PathVariable Integer id) {
		return mav("orderItem.html");
	}

	@RequestMapping(value = "/ui/brandReport")
	public ModelAndView brandReport() {
		return mav("brandReport.html");
	}

	@RequestMapping(value = "/ui/inventoryReport")
	public ModelAndView inventoryReport() {
		return mav("inventoryReport.html");
	}

	@RequestMapping(value = "/ui/salesReport")
	public ModelAndView salesReport() {
		return mav("salesReport.html");
	}
	@RequestMapping(value = "/ui/scheduler")
	public ModelAndView dailySalesReport() {
		return mav("dailySales.html");
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
	}

	@RequestMapping(value = "/ui/signup")
	public ModelAndView signup() {
		return mav("signup.html");
	}


	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}

}
