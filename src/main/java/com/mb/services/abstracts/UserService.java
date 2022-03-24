package com.mb.services.abstracts;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mb.demo.model.Role;
import com.mb.demo.model.User;

public interface UserService extends UserDetailsService{

	
	Optional<User> findById(Long id);
	
	void addUser(User user);
	
	void addRoleToUser(String username, String roleName);
	
	void saveRole(Role role);
}
