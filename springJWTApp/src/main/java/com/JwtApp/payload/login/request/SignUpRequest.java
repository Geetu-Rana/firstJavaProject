package com.JwtApp.payload.login.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class SignUpRequest {
	@NotNull
	@NotBlank
	@Size(min =3,max = 30)
	private String username;
	
	@NotBlank
	@NotNull
	private String password;
	@Email
	private String email;
	@NotEmpty
	private Set<String> roles;
	
	
}
