package com.starter.springsecurityJWT.models;

public class AuthenticateRes {

	private final String jwt;

	public AuthenticateRes(String jwt) {
		super();
		this.jwt = jwt;
	}


	public String getJwt() {
		return jwt;
	}
}
