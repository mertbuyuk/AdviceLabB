package com.mb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.Post;
import com.mb.services.concretes.PostManager;

@RestController
@RequestMapping("api/post")
public class PostController {

	@Autowired
	PostManager postManager;
	
	
	@PostMapping("add")
	private void addPost(Post post) {
		postManager.addPost(post);	
	}
	
	@GetMapping("getPost")
	private Post getPostByName(String name) {
		return postManager.findPostByFilmName(name);
	}
}
