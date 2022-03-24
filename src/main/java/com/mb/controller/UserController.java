package com.mb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.Role;
import com.mb.demo.model.User;
import com.mb.jwt.AuthRequest;
import com.mb.jwt.JwtUtil;
import com.mb.services.abstracts.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
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
	public String createToken(@RequestBody AuthRequest authRequest) throws Exception{
		try {
			System.out.println(authRequest.getPassword());
			System.out.println(authRequest.getUsername());
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("Incorret username or password", e);
		}
		
		final UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return jwt;
	}
	
}
