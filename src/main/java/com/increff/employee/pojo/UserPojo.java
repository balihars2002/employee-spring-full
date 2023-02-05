package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
public class UserPojo {

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@TableGenerator(name = "user_id", pkColumnValue = "user_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id")
	private int id;
	//private String name;
	private String email;
	private String password;
	private String role;

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
