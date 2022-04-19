package com.mb.services.abstracts;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mb.demo.model.Message;
import com.mb.demo.model.Post;
import com.mb.demo.model.User;

public interface UserService extends UserDetailsService{

	
	User findById(Long id);
	
	Message register(User user, String siteUrl) ;
	
	boolean verify(String code);
	
	User isEnabledUser(String username);
	
	User addPostToUser(Long id, Post post);
	
	List<Post> getUsersPost(Long id);
	
	List<User> getUsersFollowers(Long id);
	
	List<User> getUsersFollowed(Long id);
	
	void followById(Long fromId, Long toId);
	
	void deletebyId(Long fromId, Long toId);
	
	String saveUserPhoto(User user, MultipartFile file) throws IOException;
	
	String getUserPhoto(Long id);
}
