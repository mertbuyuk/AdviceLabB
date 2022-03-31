package com.mb.services.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mb.demo.model.Post;
import com.mb.demo.model.User;

public interface UserService extends UserDetailsService{

	
	User findById(Long id);
	
	String register(User user, String siteUrl) ;
	
	boolean verify(String code);
	
	User isEnabledUser(String username);
	
	User addPostToUser(Long id, Post post);
	
	List<Post> getUsersPost(Long id);
}
