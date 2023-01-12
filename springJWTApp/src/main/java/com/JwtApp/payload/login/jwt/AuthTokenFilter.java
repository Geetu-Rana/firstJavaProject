package com.JwtApp.payload.login.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.JwtApp.login.service.UserDetailServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtils jUtils;
	
	@Autowired
	private UserDetailServiceImpl udsImpl;

	
	private static final Logger logger  = LoggerFactory.getLogger(AuthTokenFilter.class);


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			
			
			String jwt = parseJwt(request);
			if(jwt != null && jUtils.validateJwtToken(jwt)) {
				UserDetails userDetails = udsImpl.loadUserByUsername(jwt);
				UsernamePasswordAuthenticationToken authenticationToken = 
							new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Cannot set User Authentication: {}",e);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest req) {
		
		String jwt = jUtils.getJwtFromCookies(req);
		return jwt;
	}
	
	
	
}
