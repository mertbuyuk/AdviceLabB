package com.mb.services.abstracts;

import com.mb.demo.model.Post;

public interface PostService {

	void addPost(Post post);
	
	Post findPostByFilmName(String name);
}
