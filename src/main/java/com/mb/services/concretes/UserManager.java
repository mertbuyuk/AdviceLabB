package com.mb.services.concretes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mb.dao.UserDao;
import com.mb.demo.model.User;
import com.mb.services.abstracts.UserService;

@Service
public class UserManager implements UserService {

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
}
