package com.JwtApp.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JwtApp.login.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
	
	Boolean existsByUserName(String userName); 
	
	Boolean existsByEmail(String email);
}
