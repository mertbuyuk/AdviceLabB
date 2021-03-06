package com.mb.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;import java.util.stream.Collector;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mb.demo.body.RelationBody;
import com.mb.demo.dtos.LoginResponse;
import com.mb.demo.dtos.RelationDto;
import com.mb.demo.dtos.UserDto;
import com.mb.demo.model.Followers;
import com.mb.demo.model.Post;
import com.mb.demo.model.Relation;
import com.mb.demo.model.User;
import com.mb.demo.responses.PostResponse;
import com.mb.demo.responses.Response;
import com.mb.jwt.AuthRequest;
import com.mb.jwt.JwtUtil;
import com.mb.services.abstracts.UserService;
import com.mb.services.concretes.UserManager;

@RestController
@RequestMapping("api/user/")
public class UserController {
	
	@Autowired
	UserManager userManager;

	
	@GetMapping("findById/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		User user = userManager.findById(id);
		LoginResponse userResponse = new LoginResponse(user.getId(), user.getFirstName(), user.getEmail(),null,user.getPhoto());
		return Response.ok("Succesful").body(userResponse).build();
	}
	
	@PostMapping("addPosttoUser/{id}")
	public ResponseEntity<?> addPosttoUser(@PathVariable Long id,@RequestBody Post post) {
		User user = userManager.addPostToUser(id,post);
		if(user ==  null) return Response.notFound("not found").build();
	
		//buras??n?? postresponse ile de??i??tir 
		LoginResponse userResponse = new LoginResponse(user.getId(), user.getFirstName(), user.getEmail(),null,null);
		return Response.ok("Succesful").body(userResponse).build();
	}
	
	@GetMapping("getPostById/{id}")
	public ResponseEntity<?>  findPostUsers(@PathVariable Long id) {
		
		List<Post> list =   userManager.getUsersPost(id);
		
		List<PostResponse> responsePost = list.stream()
				.map(this::converToPostResponse)
                .collect(Collectors.toList());
				
		return Response.ok("Succesful").body(responsePost).build();
	}
	
	@GetMapping("getFollowedList/{id}")
	public ResponseEntity<?> getFollowedList(@PathVariable Long id) {
		List<User> s = userManager.getUsersFollowed(id);
		
		List<UserDto> userDtoList = s.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
		
		 return  Response.ok("Succesful").body(userDtoList).build();
	}
	
	@GetMapping("getFollowerList/{id}")
	public ResponseEntity<?> getFollowerList(@PathVariable Long id) {
		List<User> s = userManager.getUsersFollowers(id);
		
		List<UserDto> userDtoList = s.stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
		
		 return  Response.ok("Succesful").body(userDtoList).build();
	}
	
	@PostMapping("followById")
	public ResponseEntity<?> followById(@RequestBody RelationBody relBody) {
		
		userManager.followById(relBody.getFromId(), relBody.getToId());
		
		return Response.ok("Succesful").body("").build();
	}
	
	@PostMapping("deleteById")
	public ResponseEntity<?> deleteById(@RequestBody RelationBody relBody) {
		
		userManager.deletebyId(relBody.getFromId(), relBody.getToId());
		
		return Response.ok("Succesful").body("").build();}
	
	@PostMapping("savephoto/{id}")
    public ResponseEntity<?> saveUserPhoto(@PathVariable Long id,
    		@RequestParam("image") MultipartFile multipartFile) {
		
		try {
			userManager.saveUserPhoto(id, multipartFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return Response.ok("Succes").body("").build();
	}
	
	@GetMapping("getphoto/{id}")
    public ResponseEntity<?> getUserPhoto(@PathVariable Long id) {
		
		byte[] path = userManager.getUserPhoto(id);
		if(path == null) return Response.notFound("User null").body("").build();
		
		return Response.ok("Succes").body(path).build();
		
	}
	
	@GetMapping("getCount/{id}")
	public ResponseEntity<?> getCountofRelations(@PathVariable Long id){
		
	    Relation relation =userManager.getCountOfRelations(id);
		
		return Response.ok("Succesfull").body(relation).build();	}	
	
	@GetMapping("searchUsers")
	public ResponseEntity<?> searchUsers(@RequestParam String keyword){
		
		List<User> users = userManager.searchByName(keyword);
		
		List<UserDto> userDto = users.stream()
				.map(this::toUserDto)
				.collect(Collectors.toList());
		System.out.println(userDto.size());
		return Response.ok("success").body(userDto).build();
	}
	
  	public UserDto toUserDto(User user) {
  	  
 		 UserDto userDto = new UserDto(user.getId(),user.getFirstName(),user.getPhoto());
 		 
 		return userDto;
 }
  	
  	private UserDto convertToUserDto(User user) {
		UserDto dto = new UserDto(user.getId(), user.getFirstName());
		return dto;
	}
	
	private PostResponse converToPostResponse(Post post) {
		PostResponse postResponse = new PostResponse(post.getId(), post.getFilmName());
		return postResponse;
	}
}
