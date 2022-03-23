package com.mb.services.concretes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mb.dao.UserDao;
import com.mb.demo.model.User;
import com.mb.services.abstracts.UserService;

@Service
public class UserManager implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public Optional<User> findById(Long id) {
		
		return userDao.findById(id);
	}

	@Override
	public void addUser(User user) {
		
		userDao.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user =userDao.getUserByFirstName(username);
		 if(user == null) {
			 throw new UsernameNotFoundException("Not found");
		 }
		 
		 
		return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(), null);
	}
}
