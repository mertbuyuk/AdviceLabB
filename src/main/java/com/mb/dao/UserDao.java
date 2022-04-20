package com.mb.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mb.demo.dtos.RelationDto;
import com.mb.demo.dtos.UserDto;
import com.mb.demo.model.*;


public interface UserDao extends JpaRepository<User,Long> {
	
	User getById(Long id);
	
	@Query("SELECT a FROM User a Where a.firstName= ?1")
	User getUserByFirstName(String username);
	
	@Query("SELECT a FROM User a Where a.verificationCode= ?1")
	User findByVerification(String code);
	
	boolean existsByEmail(String email);
	
	boolean existsByFirstName(String name);
	
	User getUserPostsById(Long id);
	
	//@Query(value= "SELECT * FROM FOLLOWING INNER JOIN USER on USER.id = FOLLOWING.USER_ID where USER_ID = ?1 AND FOLLOWED_ID <>?1",nativeQuery = true)
	//List<User> getUserFollowersId(Long id);
	
	
}
