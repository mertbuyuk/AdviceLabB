package com.mb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mb.demo.model.Post;

public interface PostDao extends JpaRepository<Post, Long> {
	
	Post findByFilmName(String name);
	
	Post getById(Long id);
	
	@Query(value = "SELECT * FROM POST  where POST.film_name LIKE %?1%", nativeQuery = true)
	List<Post> searchPostByName(String name);

}
