package com.mb.demo.dtos;

import java.util.List;

import com.mb.demo.model.User;

public class UserDto {

	private Long id;

	private String name;
	
	private byte[] photo;

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public UserDto(Long id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
    public UserDto(Long id, String name, byte[] photo) {
		
		this.id = id;
		this.name = name;
		this.photo = photo;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	
}
