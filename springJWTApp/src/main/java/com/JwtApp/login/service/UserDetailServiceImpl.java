package com.JwtApp.login.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.JwtApp.login.model.User;
import com.JwtApp.login.repository.UserRepository;
@Service
public class UserDetailServiceImpl  implements UserDetailsService{
	@Autowired
	private UserRepository uRepo;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = uRepo.findByUserName(username)
					.orElseThrow(()-> new UsernameNotFoundException("No User Exst with userName "+username) );
		return UserDetailImp.Buil(user);
	}

}
