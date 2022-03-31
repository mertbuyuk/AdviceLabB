package com.mb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.PostType;
import com.mb.services.concretes.PostTypeManager;

@RestController
@RequestMapping("/api/posttype/")
public class PostTypeController {
	
	@Autowired
	PostTypeManager postTypeManager;

	@GetMapping("getPostTypes")
	private ResponseEntity<?> getAllPostTypes(){
		List<PostType> pTypeList = postTypeManager.getPostTypes();
		return  Response.ok("Succes").body(pTypeList).build();
	}
}
