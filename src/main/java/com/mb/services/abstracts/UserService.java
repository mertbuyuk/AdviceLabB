package com.mb.services.abstracts;

import java.util.Optional;

import com.mb.demo.model.User;

public interface UserService{

	
	Optional<User> findById(Long id);
	
	void addUser(User user);
}
