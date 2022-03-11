package com.mb.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.demo.model.*;


public interface UserDao extends JpaRepository<User,Long> {
	
	Optional<User> findById(Long id);
	
	User getUserByFirstName(String username);
}
