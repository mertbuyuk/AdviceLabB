package com.mb.demo.dtos;

public class LoginResponse {
	
	private Long id;
	private String firstName;
	private String email;
	private String jwt;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getJwt() {
		return jwt;
	}


	public void setJwt(String jwt) {
		this.jwt = jwt;
	}


	public LoginResponse(Long id, String firstName,String email,String jwt) {
		this.id = id;
		this.firstName = firstName;
		this.email = email;
		this.jwt = jwt;
	}
}
