package com.mb.dao;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mb.demo.dtos.RelationDto;
import com.mb.demo.model.Followers;
public interface RelationDao  extends CrudRepository<Followers, Long> {

	
	@Query(value= "SELECT * FROM FOLLOWERS where FROM_USER_FK= ?1",nativeQuery = true)
	List<Followers> getUserFollowedId(Long id);
	
	@Query(value= "SELECT * FROM FOLLOWERS where TO_USER_FK= ?1",nativeQuery = true)
	List<Followers> getUserFollowerId(Long id);
	

	@Query(value= "Select * FROM FOLLOWERS where FROM_USER_FK= ?1 and TO_USER_FK = ?2",nativeQuery = true)
	Followers deleteRelation(Long fromId, Long toId);
}
