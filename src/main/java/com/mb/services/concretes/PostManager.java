package com.mb.services.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.dao.PostDao;
import com.mb.demo.model.Post;
import com.mb.services.abstracts.PostService;

@Service
public class PostManager implements PostService {
	
	@Autowired
	PostDao postDao;

	@Override
	public void addPost(Post post) {
		postDao.save(post);
	}

	@Override
	public Post findPostByFilmName(String name) {
		
		return postDao.findByFilmName(name);
	}

}
