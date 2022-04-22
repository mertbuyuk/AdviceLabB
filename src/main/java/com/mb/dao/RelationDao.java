package com.mb.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mb.demo.dtos.RelationDto;
import com.mb.demo.model.Followers;
import com.mb.demo.model.Relation;
public interface RelationDao  extends CrudRepository<Followers, Long> {

	
	@Query(value= "SELECT * FROM FOLLOWERS where FROM_USER_FK= ?1",nativeQuery = true)
	List<Followers> getUserFollowedId(Long id);
	
	@Query(value= "SELECT * FROM FOLLOWERS where TO_USER_FK= ?1",nativeQuery = true)
	List<Followers> getUserFollowerId(Long id);
	

	@Query(value= "Select * FROM FOLLOWERS where FROM_USER_FK= ?1 and TO_USER_FK = ?2",nativeQuery = true)
	Followers deleteRelation(Long fromId, Long toId);
	
	//SELECT 
    //(SELECT COUNT(TO_USER_FK) FROM FOLLOWERS WHERE FROM_USER_FK = 35) AS FOLLOWING,
    //(SELECT COUNT(FROM_USER_FK )  FROM FOLLOWERS WHERE TO_USER_FK = 35) AS FOLLOWER 
	
    @Query(value = "SELECT (SELECT COUNT(f.id) FROM FOLLOWERS f WHERE f.FROM_USER_FK = ?1) as follower ,(SELECT COUNT(t.id) FROM FOLLOWERS t WHERE t.TO_USER_FK = ?1 ) as following",nativeQuery = true)
    Collection<Relation> getCountofRelations(Long fromId);
}
