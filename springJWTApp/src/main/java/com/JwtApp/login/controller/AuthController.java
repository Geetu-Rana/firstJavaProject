package com.JwtApp.login.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.JwtApp.login.model.ERole;
import com.JwtApp.login.model.Role;
import com.JwtApp.login.model.User;
import com.JwtApp.login.repository.RoleRepository;
import com.JwtApp.login.repository.UserRepository;
import com.JwtApp.login.service.UserDetailImp;
import com.JwtApp.payload.login.jwt.JwtUtils;
import com.JwtApp.payload.login.request.LoginRequest;
import com.JwtApp.payload.login.request.SignUpRequest;
import com.JwtApp.payload.login.response.MessageResponse;
import com.JwtApp.payload.login.response.UserInfoResponse;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins ="*", maxAge = 3600)
public class AuthController {
	
	@Autowired 
	private AuthenticationManager autManager;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository uRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private JwtUtils jUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticationUser( @Valid @RequestBody LoginRequest loginRequest ){
		Authentication authentication = autManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailImp uDetails = (UserDetailImp)authentication.getPrincipal();
		
		ResponseCookie jwtCookie = jUtils.generateJwtCookie(uDetails);
		
		List<String> roles = uDetails.getAuthorities().stream()
								.map(item -> item.getAuthority())
								.collect(Collectors.toList());
		
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
					.body(new UserInfoResponse(uDetails.getUser_Id(),uDetails.getUsername(),
												uDetails.getEmail(),
												roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> resgisterUser(@Valid @RequestBody SignUpRequest signUpRequest){
		if(uRepository.existsByUserName(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username Already Taken!" ));
		}
		if(uRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<String>("Error : User Email Already Exist!",HttpStatus.BAD_REQUEST );
		}
		 
		User user = new User();
		user.setUserName(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(encoder.encode(signUpRequest.getPassword() ));
		
		Set<String> strRole = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		
		if(strRole==null) {
			Role role = new Role();
			role.setRoleName(ERole.USER);
			roles.add(role);
			
		}else {
			strRole.forEach(role -> {
				switch (role.toUpperCase()) {
				
				case "ADMIN": 
					Optional<Role> adminRole = roleRepository.findByRoleName(ERole.ADMIN);
					
					
					roles.add(adminRole.get());
					break;
				case "MOD":
					Role modRole = roleRepository.findByRoleName(ERole.MODERATOR);
					roles.add(modRole);
					break;

				default:
					Role userRole = roleRepository.findByRoleName(ERole.USER)
					.orElseThrow(()-> new RuntimeException("UserRole Not Exist"));
					roles.add(userRole);
					break;
				}
			});	
		}
		user.setRoles(roles);
		uRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successFully"));
	}

	public ResponseEntity<?> logoutUser(){
		ResponseCookie cookie = jUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("Looged out successfully...."));
	}
	
}
