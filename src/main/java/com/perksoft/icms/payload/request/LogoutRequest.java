package com.perksoft.icms.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LogoutRequest {

	@NotBlank
	private String username;
	@NotBlank
	private String token;
}
