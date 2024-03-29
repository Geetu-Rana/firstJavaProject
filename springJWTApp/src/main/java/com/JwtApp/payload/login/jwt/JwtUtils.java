package com.JwtApp.payload.login.jwt;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.JwtApp.login.service.UserDetailImp;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${login.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${login.app.jwtExpirationMs}")
	private Integer jwtExpirationMs;
	
	@Value("${login.app.jwtCookieName}")
	private String jwtCookies;
	
	public String getJwtFromCookies(HttpServletRequest req) {
		Cookie cookie = WebUtils.getCookie(req, jwtCookies);
		if(cookie != null) {
			return cookie.getValue();
		}
		return null;
	}
	
	public ResponseCookie generateJwtCookie(UserDetailImp userPrinciple) {
		String jwt = generateTokenFromUsername(userPrinciple.getUsername());
		ResponseCookie cookie = ResponseCookie.from(jwtCookies, jwt).path("/api").maxAge(24*60*60).httpOnly(true).build();
		return cookie;
	}
	
	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookies, null).path("/api").build();
		return cookie;
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret)
				.parseClaimsJws(token).getBody().getSubject();
		
	}
	public boolean validateJwtToken(String authToken) {
		
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature : {}",e.getMessage());
		} catch (MalformedJwtException e) {
		    logger.error("Invalid JWT token: {}", e.getMessage());
	    } catch (ExpiredJwtException e) {
	        logger.error("JWT token is expired: {}", e.getMessage());
	    } catch (UnsupportedJwtException e) {
	        logger.error("JWT token is unsupported: {}", e.getMessage());
	    } catch (IllegalArgumentException e) {
	        logger.error("JWT claims string is empty: {}", e.getMessage());
	    }
		return false;
	}
	
	  public String generateTokenFromUsername(String username) { 
		  return Jwts.builder()
				  .setSubject(username)
				  .setIssuedAt(new Date())
				  .setExpiration(new Date( (new Date()).getTime() +jwtExpirationMs ))
				  .signWith(SignatureAlgorithm.HS256, jwtSecret)
				  .compact();
	  }
}
