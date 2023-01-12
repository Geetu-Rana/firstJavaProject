package com.JwtApp.payload.login.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
	
	private Integer user_ID;
	
	private String username;
	
	private String email;
	
	private List<String> roles;
	
	
}
