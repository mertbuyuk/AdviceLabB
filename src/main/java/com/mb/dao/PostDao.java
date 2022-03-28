package com.mb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mb.demo.model.Post;

public interface PostDao extends JpaRepository<Post, Long> {
	
	Post findByFilmName(String name);

}
