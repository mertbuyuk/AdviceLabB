package com.mb.demo.model;

public class Relation {
	
	private Long following;
	
	private Long follower;

	public Long getFollowing() {
		return following;
	}

	public Relation() {

	}

	public Relation(Long following, Long follower) {
		this.following = following;
		this.follower = follower;
	}

	public void setFollowing(Long following) {
		this.following = following;
	}

	public Long getFollower() {
		return follower;
	}

	public void setFollower(Long follower) {
		this.follower = follower;
	}
}
