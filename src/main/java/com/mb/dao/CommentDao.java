package com.mb.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mb.demo.dtos.CommentDto;
import com.mb.demo.model.Comment;

public interface CommentDao extends JpaRepository<Comment, Long> {
	
	@Query(value= "SELECT * FROM COMMENT c INNER JOIN USER u ON c.USER_ID = u.ID  WHERE c.POST_ID =?1",nativeQuery = true)
	List<Comment> getComments(Long postId);
}
