package com.starter.springsecurityJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.starter.springsecurityJWT.configurer.JwtUtill;
import com.starter.springsecurityJWT.models.AuthenticateReq;
import com.starter.springsecurityJWT.models.AuthenticateRes;
import com.starter.springsecurityJWT.service.MyUserDetailsService;

@RestController
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticatemanager;
	
	@Autowired
	private MyUserDetailsService myuserDetailsService;
	
	@Autowired
	private JwtUtill jwtutill;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String helloWorld() {
		return "hello world";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticateReq request) throws Exception {

		try {
			authenticatemanager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
				
   		final UserDetails username = myuserDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = jwtutill.generateToken(username);
		return ResponseEntity.ok(new AuthenticateRes(jwt));

	}
	
	@RequestMapping(value = "/getusers", method = RequestMethod.GET)
	public String getUsers() {
		return "usersList";
	}
}
