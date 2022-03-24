package com.mb.services.concretes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mb.dao.RoleDao;
import com.mb.dao.UserDao;
import com.mb.demo.model.Role;
import com.mb.demo.model.User;
import com.mb.services.abstracts.UserService;

@Service
public class UserManager implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
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
		 
		 Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		 user.getRoles().forEach(role -> {
			 authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
					 });
		 
		return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(), authorities);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		User user = userDao.getUserByFirstName(username);
		Role role = roleDao.findByName(roleName);
		
		user.getRoles().add(role);
	}

	@Override
	public void saveRole(Role role) {
		roleDao.save(role);
		
	}
}
