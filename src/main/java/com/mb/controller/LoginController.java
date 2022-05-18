package com.mb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.dtos.LoginResponse;
import com.mb.demo.model.User;
import com.mb.demo.responses.Response;
import com.mb.jwt.AuthRequest;
import com.mb.jwt.JwtUtil;
import com.mb.services.concretes.UserManager;

@RestController
@RequestMapping("api/auth/")
public class LoginController {
	@Autowired 
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserManager userManager;

	@PostMapping("/login")
	public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) throws Exception{
		try {
			User user = userManager.isEnabledUser(authRequest.getUsername());
			if(!user.isEnabled()) {
				Response response = new Response();
				response.setStatus(HttpStatus.UNAUTHORIZED);
				response.setResponseBody("Please Verify your email");
				return response.build();
			}
			
			
			Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			String token = jwtUtil.generateToken(authenticate);
			LoginResponse loginResponse = new LoginResponse(user.getId(), user.getFirstName(), user.getEmail(),"Bearer "+ token,null);
			return Response.ok("Login Succes").body(loginResponse).build();
					
		} catch (BadCredentialsException  e) {
			
			return ResponseEntity.status(401).body(e.getMessage());
		}
	
	}
}
