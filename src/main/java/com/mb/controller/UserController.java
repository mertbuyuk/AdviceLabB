package com.mb.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.mb.demo.dtos.LoginResponse;
import com.mb.demo.dtos.UserDto;
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

	
	@GetMapping("findById")
	public ResponseEntity<?> findById(@RequestParam Long id) {
		User user = userManager.findById(id);
		LoginResponse userResponse = new LoginResponse(user.getId(), user.getFirstName(), user.getEmail(),null);
		return Response.ok("Succesful").body(userResponse).build();
	}
	
	@PostMapping("addPosttoUser")
	public ResponseEntity<?> addPosttoUser(@RequestParam Long id,@RequestBody Post post) {
		User user = userManager.addPostToUser(id,post);
		if(user ==  null) return Response.notFound("not found").build();
	
		//burasını postresponse ile değiştir 
		LoginResponse userResponse = new LoginResponse(user.getId(), user.getFirstName(), user.getEmail(),null);
		return Response.ok("Succesful").body(userResponse).build();
	}
	
	@GetMapping("getPostByid")
	public List<Post> findPostUsers(@RequestParam Long id) {
		
		return userManager.getUsersPost(id);
	}
	
	@GetMapping("getFollowedList")
	public List<UserDto> getFollowedList(@RequestParam Long id) {
		List<User> s = userManager.getUsersFollowed(id);
		
		 return s.stream()
                .map(this::convertToUserDto)
                .collect(Collectors.toList());
	}
	
	@PostMapping("followById")
	public ResponseEntity<?> followById(@RequestParam Long fromId,@RequestParam  Long toId) {
		
		userManager.followById(fromId, toId);
		
		return Response.ok("Succesful").body("").build();
	}
	
	@PostMapping("deleteById")
	public ResponseEntity<?> deleteById(@RequestParam Long fromId,@RequestParam  Long toId) {
		
		userManager.deletebyId(fromId, toId);
		
		return Response.ok("Succesful").body("").build();}
	
	
	private UserDto convertToUserDto(User user) {
		UserDto dto = new UserDto(user.getId(), user.getFirstName());
		return dto;
	}
	
	@PostMapping("save/photo")
    public ResponseEntity<?> saveUserPhoto(@RequestBody User user,
            @RequestParam("image") MultipartFile multipartFile) {
		
		try {
			userManager.saveUserPhoto(user, multipartFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return Response.ok("Succes").body("").build();
		
	}
	
	@PostMapping("get/photo")
    public ResponseEntity<?> getUserPhoto(@RequestBody User user,
            @RequestParam("image") MultipartFile multipartFile) {
		
		String path = userManager.getUserPhoto(user.getId());
		if(path == null) return Response.notFound("User null").body("").build();
		
		return Response.ok("Succes").body(path).build();
		
	}
}
