package com.stautisabela.userauthenticationsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stautisabela.userauthenticationsystem.data.vo.v1.AccountCredentialsVO;
import com.stautisabela.userauthenticationsystem.data.vo.v1.TokenVO;
import com.stautisabela.userauthenticationsystem.repositories.UserRepository;
import com.stautisabela.userauthenticationsystem.security.JwtTokenProvider;

@Service
public class AuthService {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	// authenticates user credentials, retrieves user with those credentials, and generates token for user
	@SuppressWarnings("rawtypes")
	public ResponseEntity signIn(AccountCredentialsVO data) {
		if (areParamsInvalid(data)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials.");
		}
		
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
			var user = repository.findByUsername(username);
			var tokenResponse = new TokenVO();
			
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found.");
			}
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username or password.");
		}
	}
	
	// generates new access token for an existing refresh token
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String refreshToken) {
		if (areParamsInvalid(username, refreshToken)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or token.");
		}
		
		var user = repository.findByUsername(username);
		var tokenResponse = new TokenVO();
			
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username " + username + " not found.");
		}
		return ResponseEntity.ok(tokenResponse);
	}
	
	
	private boolean areParamsInvalid(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank() 
				|| data.getPassword() == null || data.getPassword().isBlank();
	}
		
	private boolean areParamsInvalid(String username, String refreshToken) {
		return username == null || username.isBlank() || refreshToken == null || refreshToken.isBlank();
	}
}
