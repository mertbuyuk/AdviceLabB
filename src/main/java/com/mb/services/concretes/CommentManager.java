package com.mb.services.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.dao.CommentDao;
import com.mb.demo.model.Comment;
import com.mb.services.abstracts.CommentService;

@Service
public class CommentManager implements CommentService {

	@Autowired
	CommentDao commentDao;
	
	@Override
	public List<Comment> getPostComments(Long postId) {
		
		return commentDao.getComments(postId);
	}
	
	public void save(Comment comment) {
		commentDao.save(comment);
	}

}
