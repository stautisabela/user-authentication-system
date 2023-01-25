package com.stautisabela.userauthenticationsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stautisabela.userauthenticationsystem.data.vo.v1.AccountCredentialsVO;
import com.stautisabela.userauthenticationsystem.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService service;
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value="/signin")
	public ResponseEntity signIn(@RequestBody AccountCredentialsVO data) {
		
		if (isParamValid(data)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid credentials.");
		}
		var token = service.signIn(data);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token generation.");
		}
		return token;
	}

	private boolean isParamValid(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank() 
				|| data.getPassword() == null || data.getPassword().isBlank();
	}
}
