package com.mb.demo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	public enum UserType{
	        NORMAL,
	        ADMIN
	    }
	//lombok can use for getters setters but its not working sometimes on eclipse because of configs idk why
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonBackReference
	private Long id;  
	
	@Column(name = "verification_code", length = 64)
	private String verificationCode; 
	
	private boolean enabled = false;
	
	public void setId(Long id) {
		this.id = id;
	}
	@Column(unique = true)
	private String firstName;
	private String email;
	private String password;
	
	@Column
	private UserType userType;
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Post> post = new ArrayList<>();
	
	public List<Followers> getFollowers() {
		return followers;
	}
	
	@OneToMany(mappedBy="to",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Followers> followers;

    @OneToMany(mappedBy="from",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Followers> followeds;
    
	public void setFollowers(List<Followers> followers) {
		this.followers = followers;
	}

	public List<Followers> getFolloweds() {
		return followeds;
	}

	public void setFolloweds(List<Followers> followeds) {
		this.followeds = followeds;
	}

	public List<Post> getPost() {
		return post;
	}

	public String getFirstName() {
		return firstName;
	}
	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getId() {
		return id;
	}

	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	} 

}
