package com.JwtApp.login.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.JwtApp.login.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailImp  implements UserDetails {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer user_Id;
	
	private String userName;
	
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UserDetailImp Buil(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role->new SimpleGrantedAuthority(role.getRoleName().name()) )
				.collect(Collectors.toList());
		return new UserDetailImp(
					user.getUser_Id(), 
					user.getUserName(),  
					user.getEmail(),
					user.getPassword(),
					authorities);
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		return authorities;
	}
	
	
	
	 public Integer getUser_Id() {
		    return user_Id;
		  }

	public String getEmail() {
		    return email;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
}
