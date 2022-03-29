package com.mb.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mb.demo.model.*;


public interface UserDao extends JpaRepository<User,Long> {
	
	Optional<User> findById(Long id);
	
	User getUserByFirstName(String username);
	
	@Query("SELECT a FROM User a Where a.verificationCode= ?1 ")
	User findByVerification(String code);
}
