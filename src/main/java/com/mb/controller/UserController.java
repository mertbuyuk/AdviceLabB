package com.mb.controller;

import java.util.List;
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

import com.mb.demo.dtos.LoginResponse;
import com.mb.demo.model.Post;
import com.mb.demo.model.User;
import com.mb.jwt.AuthRequest;
import com.mb.jwt.JwtUtil;
import com.mb.services.abstracts.UserService;
import com.mb.services.concretes.UserManager;

@RestController
@RequestMapping("api/user/")
public class UserController {
	
	@Autowired
	UserManager userManager;

	
	@GetMapping("/findById")
	public ResponseEntity<?> findById(@RequestParam Long id) {
		User user = userManager.findById(id);
		LoginResponse userResponse = new LoginResponse(user.getId(), user.getFirstName(), user.getEmail(),null);
		return Response.ok("Succesful").body(userResponse).build();
	}
	
	@PostMapping("/addPosttoUser")
	public ResponseEntity<?> addPosttoUser(@RequestParam Long id,@RequestBody Post post) {
		User user = userManager.addPostToUser(id,post);
		if(user ==  null) return Response.notFound("not found").build();
	
		//burasını postresponse ile değiştir 
		LoginResponse userResponse = new LoginResponse(user.getId(), user.getFirstName(), user.getEmail(),null);
		return Response.ok("Succesful").body(userResponse).build();
	}
	
	@GetMapping("/getPostByid")
	public List<Post> findPostUsers(@RequestParam Long id) {
		
		return userManager.getUsersPost(id);
	}
}
