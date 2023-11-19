package com.security.jwt.model;

public class JwtResponse {
	private String jwtToken;

	
	
	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public JwtResponse() {
		
	}

	public JwtResponse(String jwtToken) {
		
		this.jwtToken = jwtToken;
	}
	
}
