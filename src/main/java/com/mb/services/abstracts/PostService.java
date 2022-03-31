package com.mb.services.abstracts;

import java.util.List;

import com.mb.demo.model.Post;

public interface PostService {

	void addPost(Post post);
	
	Post findPostByFilmName(String name);
	
	List<Post> getAllPost();
	
	Post findById(Long id);
}
