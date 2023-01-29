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
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value="/signin")
	public ResponseEntity signIn(@RequestBody AccountCredentialsVO data) {
		var token = service.signIn(data);;
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token generation.");
		}
		return token;
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value="/refresh/{username}")
	public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
		var token = service.refreshToken(username, refreshToken);
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token generation.");
		}
		return token;
	}
}
