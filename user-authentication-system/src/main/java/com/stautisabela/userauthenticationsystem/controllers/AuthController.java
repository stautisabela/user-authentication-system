package com.stautisabela.userauthenticationsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stautisabela.userauthenticationsystem.data.vo.v1.AccountCredentialsVO;
import com.stautisabela.userauthenticationsystem.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService service;
	
	// authenticates user credentials and returns token
	@SuppressWarnings("rawtypes")
	@PostMapping(value="/signin")
	public ResponseEntity signIn(@RequestBody AccountCredentialsVO data) {
		
		if (areParamsInvalid(data)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials.");
		}
		var token = service.signIn(data);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token generation.");
		}
		return token;
	}
	
	// generates new access token for an existing refresh token
		@SuppressWarnings("rawtypes")
		@PutMapping(value="/refresh/{username}")
		public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
			
			if (areParamsInvalid(username, refreshToken)) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or token.");
			}
			var token = service.refreshToken(username, refreshToken);
			if (token == null) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token generation.");
			}
			return token;
		}

	// checks if username and password are null or blank
	private boolean areParamsInvalid(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank() 
				|| data.getPassword() == null || data.getPassword().isBlank();
	}
	
	// checks if username and refresh token are null or blank
		private boolean areParamsInvalid(String username, String refreshToken) {
			return username == null || username.isBlank() || refreshToken == null || refreshToken.isBlank();
		}
	
}
