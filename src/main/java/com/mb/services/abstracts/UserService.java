package com.mb.services.abstracts;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.mb.demo.model.User;

public interface UserService extends UserDetailsService{

	
	Optional<User> findById(Long id);
	
	String register(User user, String siteUrl) ;
	
	boolean verify(String code);
	
	User isEnabledUser(String username);
}
