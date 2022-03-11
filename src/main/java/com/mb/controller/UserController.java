package com.mb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.User;
import com.mb.services.abstracts.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@PostMapping("/add")
	public void add(@RequestBody User user) {
		this.userService.addUser(user);
	}
	
	@GetMapping("/findById")
	public Optional<User> findById(Long id) {
		
		return this.userService.findById(id);
	}
	
}
