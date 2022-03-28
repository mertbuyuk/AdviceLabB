package com.mb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.Role;
import com.mb.demo.model.User;
import com.mb.jwt.AuthRequest;
import com.mb.jwt.JwtUtil;
import com.mb.services.abstracts.UserService;
import com.mb.services.concretes.UserManager;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	
	@Autowired
	UserManager userService;
	
	@Autowired 
	JwtUtil jwtUtil;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@PostMapping("/save")
	public void add(@RequestBody User user) {
		this.userService.addUser(user);
	}
	
	@PostMapping("/saveRole")
	public void addRole(@RequestBody Role role) {
		this.userService.saveRole(role);
	}
	
	@PostMapping("/addRoleToUser")
	public void addRoleToUser(@RequestBody User user, Role role) {
		this.userService.addRoleToUser(user.getFirstName(), role.getRoleName());
	}
	
	@GetMapping("/findById")
	public Optional<User> findById(Long id) {
		
		return this.userService.findById(id);
	}
	
	
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
