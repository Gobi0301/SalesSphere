package com.scube.crm.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLogin {

	@NotNull
	@Size(min = 4, max = 20)
	private String userName;

	@NotNull
	@Size(min = 4, max = 8)
	private String password;

	public String getPassword() {
		return this.password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
