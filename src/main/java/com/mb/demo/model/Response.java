package com.mb.demo.model;

import java.util.List;

public class Response {
	    private String token;
	    private String username;
	    private String password;
	    
	    public Response(String jwt, String username, String password) {
		
		    this.token =jwt;
	        this.username = username;
	        this.password = password;
	        
	    }
	
}
