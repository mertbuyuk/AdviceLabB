package com.mb.services.concretes;

import java.util.List;

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

	@Override
	public List<Post> getAllPost() {
		
		return postDao.findAll();
	}

	@Override
	public Post findById(Long id) {
	
		return postDao.getById(id);
	}

	@Override
	public List<Post> searchByName(String name) {
 
		return postDao.searchPostByName(name);
	}

}
