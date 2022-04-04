package com.mb.dao;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mb.demo.model.Followers;
public interface RelationDao  extends CrudRepository<Followers, Integer> {

	
	@Query(value= "SELECT * FROM FOLLOWERS where FROM_USER_FK= ?1",nativeQuery = true)
	List<Followers> getUserFollowedId(Long id);
}
