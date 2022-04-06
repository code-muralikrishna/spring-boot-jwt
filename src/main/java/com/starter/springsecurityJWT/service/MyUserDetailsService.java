package com.starter.springsecurityJWT.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return new User("murali", "pass", new ArrayList<>());
	}
}
