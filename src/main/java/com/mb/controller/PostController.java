package com.mb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mb.dao.CommentDao;
import com.mb.demo.dtos.CommentDto;
import com.mb.demo.model.Comment;
import com.mb.demo.model.Post;
import com.mb.demo.model.User;
import com.mb.services.concretes.CommentManager;
import com.mb.services.concretes.PostManager;
import com.mb.services.concretes.UserManager;

@RestController
@RequestMapping("api/post/")
public class PostController {

	@Autowired
	PostManager postManager;
	
	@Autowired
	UserManager userManager;
	
	@Autowired
	CommentManager commentManager;
	


	@GetMapping("getAllPost")
	private List<Post> getAll() {
		return postManager.getAllPost();
	}
	
	@GetMapping("getPost")
	private Post getPostByName(String name) {
		return postManager.findPostByFilmName(name);
	}
	
	@GetMapping("searchPost")
	private List<Post> searchPost(@RequestParam String keyword){
		if (keyword!=null) {
			//todo get posts with that keyword
			return null;
		}
		
		return postManager.getAllPost();
	}
	
	@PostMapping("addComment")
	private void addComment(@RequestBody Comment comment,@RequestParam Long postId,@RequestParam Long userId) {
		User user = userManager.findById(userId);
		Post post = postManager.findById(postId);
		comment.setUser(user);
		comment.setPost(post);
		commentManager.save(comment);
	}
	
	@GetMapping("getComments")
	private List<CommentDto> getPostComments(@RequestParam Long postId){
		List<Comment> comments = commentManager.getPostComments(postId);
        return comments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}
	
	private CommentDto convertToDto(Comment commnet) {
		CommentDto commentDto = new CommentDto(commnet.getId(),commnet.getUser().getId(),commnet.getPost().getId(), commnet.getComment(), commnet.getUser().getFirstName());
	    
	    return commentDto;
	}
}
