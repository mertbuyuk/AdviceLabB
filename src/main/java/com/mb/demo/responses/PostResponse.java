package com.mb.demo.responses;

public class PostResponse {

	private Long id;
	
	private String filmName;

	public PostResponse(Long id, String filmName) {

		this.id = id;
		this.filmName = filmName;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFilmName() {
		return filmName;
	}

	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}
	
	
	//private photo
}
