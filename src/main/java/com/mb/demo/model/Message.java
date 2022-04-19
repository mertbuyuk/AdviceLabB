package com.mb.demo.model;

import org.springframework.http.HttpStatus;

public class Message {

	private String message ;
	private HttpStatus code;

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
