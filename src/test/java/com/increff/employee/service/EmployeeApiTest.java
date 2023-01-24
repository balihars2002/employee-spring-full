package com.increff.employee.service;

import static org.junit.Assert.assertEquals;

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
