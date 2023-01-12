package com.JwtApp.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.JwtApp.login.model.ERole;
import com.JwtApp.login.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
		Optional<Role> findByRoleName(ERole role);
}
