package com.mb.demo.dtos;

public class LoginResponse {
	
	private Long id;
	private String firstName;
	private String email;
	private String jwt;
	private int followerCont;
	private int followedCont;
	private byte[] photo;
	
	

	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}


	public int getFollowedCont() {
		return followedCont;
	}


	public void setFollowedCont(int followedCont) {
		this.followedCont = followedCont;
	}


	public int getFollowerCont() {
		return followerCont;
	}


	public void setFollowerCont(int followerCont) {
		this.followerCont = followerCont;
	}


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


	public LoginResponse(Long id, String firstName,String email,String jwt,byte[] photo) {
		this.id = id;
		this.firstName = firstName;
		this.email = email;
		this.jwt = jwt;
		this.photo = photo;
	}
}
