package com.mb.demo.dtos;

public class CommentDto {
	
	private Long id;
	private Long userId, postId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	private String comment;
	private String userName;
	public CommentDto(Long id, Long userId,Long postId, String comment, String userName) {

		this.id = id;
		this.comment = comment;
		this.userName = userName;
		this.postId = postId;
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
