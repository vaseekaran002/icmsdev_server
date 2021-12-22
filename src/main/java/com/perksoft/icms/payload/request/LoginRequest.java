package com.perksoft.icms.payload.request;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.perksoft.icms.models.Group;

public class LoginRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
