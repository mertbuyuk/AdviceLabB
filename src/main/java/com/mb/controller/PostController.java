package com.mb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.Post;
import com.mb.services.concretes.PostManager;

@RestController
@RequestMapping("api/post/")
public class PostController {

	@Autowired
	PostManager postManager;
	

	@GetMapping("getAllPost")
	private List<Post> getAll() {
		return postManager.getAllPost();
	}
	
	@GetMapping("getPost")
	private Post getPostByName(String name) {
		return postManager.findPostByFilmName(name);
	}
}
