package com.mb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.jwt.AuthRequest;
import com.mb.jwt.JwtUtil;

@RestController
@RequestMapping("api/auth/")
public class LoginController {
	@Autowired 
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<String> createToken(@RequestBody AuthRequest authRequest) throws Exception{
		try {
			System.out.println(authRequest.getPassword());
			System.out.println(authRequest.getUsername());
			Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
			
			return ResponseEntity.ok().body("token: "+jwtUtil.generateToken(authenticate));
					
		} catch (BadCredentialsException  e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(401).body(e.getMessage());
		}
	
	}
}
