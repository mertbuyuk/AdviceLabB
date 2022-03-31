package com.mb.services.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.dao.PostTypeDao;
import com.mb.demo.model.PostType;
import com.mb.services.abstracts.PostTypeService;

@Service
public class PostTypeManager implements PostTypeService {

	@Autowired
	PostTypeDao postTypeDao;
	
	@Override
	public void addType(PostType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PostType> getPostTypes() {
		
		return postTypeDao.findAll() ;
	}

}
