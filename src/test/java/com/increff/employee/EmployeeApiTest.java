package com.increff.employee;

import static org.junit.Assert.assertEquals;

import com.increff.employee.service.ApiException;
import com.increff.employee.service.EmployeeApi;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.employee.pojo.EmployeePojo;

public class EmployeeApiTest extends AbstractUnitTest {

	@Autowired
	private EmployeeApi service;

	@Test
	public void testAdd() throws ApiException {
		EmployeePojo p = new EmployeePojo();
		p.setName(" Romil Jain ");
		service.add(p);
	}

	@Test
	public void testNormalize() {
		EmployeePojo p = new EmployeePojo();
		p.setName(" Romil Jain ");
		EmployeeApi.normalize(p);
		assertEquals("romil jain", p.getName());
	}

}
