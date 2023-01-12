package com.JwtApp.payload.login.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank
	@NotNull
	private String username;
	@NotBlank
	@NotNull
	private String password;
	
	
}
