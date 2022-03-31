package com.mb.services.abstracts;

import java.util.List;

import com.mb.demo.dtos.CommentDto;
import com.mb.demo.model.Comment;

public interface CommentService {

	List<Comment> getPostComments(Long postId);
}
