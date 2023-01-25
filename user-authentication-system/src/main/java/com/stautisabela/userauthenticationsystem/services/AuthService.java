package com.stautisabela.userauthenticationsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	// receives credentials, authenticates them, retrieves the user with those credentials, and creates an access token for it
	@SuppressWarnings("rawtypes")
	public ResponseEntity signIn(AccountCredentialsVO data) {
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
}
