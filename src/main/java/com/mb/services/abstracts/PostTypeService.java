package com.mb.services.abstracts;

import java.util.List;

import com.mb.demo.model.PostType;

public interface PostTypeService {

	void addType(PostType type);
	
	List<PostType> getPostTypes();
}
