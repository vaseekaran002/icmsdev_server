package com.perksoft.icms.payload.request;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupRequest {
	
	private String id;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 40)
	private String password;
	
	@Size(max = 20)
	private String firstName;

	@Size(max = 20)
	private String lastName;

	@Size(max = 10)
	private String mobileNumber;

	private byte[] profileImage;

	private List<String> roles;
}
