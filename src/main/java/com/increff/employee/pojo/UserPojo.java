package com.increff.employee.pojo;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UserPojo extends AbstractPojo{

	@Id
	@TableGenerator(name = "user_id", pkColumnValue = "user_id", table = "table_sequence")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id")
	private int id;
	private String email;
	private String password;
	private String role;
	private Boolean isDisabled=false;


	public Boolean getDisabled() {
		return isDisabled;
	}

	public void setDisabled(Boolean disabled) {
		isDisabled = disabled;
	}
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
