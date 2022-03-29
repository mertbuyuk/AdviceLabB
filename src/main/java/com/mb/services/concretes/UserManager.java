package com.mb.services.concretes;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mb.dao.RoleDao;
import com.mb.dao.UserDao;
import com.mb.demo.model.Role;
import com.mb.demo.model.User;
import com.mb.services.abstracts.UserService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserManager implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@Override
	public Optional<User> findById(Long id) {
		
		return userDao.findById(id);
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

	@Override
	public void register(User user, String siteUrl) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String passEncode = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(passEncode);
		
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(false);
		
		userDao.save(user);
		try {
			sendVerificationEmail(user, siteUrl);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendVerificationEmail(User user, String siteURL)
	        throws MessagingException, UnsupportedEncodingException {
	    String toAddress = user.getEmail();
	    String fromAddress = "mertbuyuk.e@gmail.com";
	    String senderName = "M Tech.";
	    String subject = "Please verify your registration";
	    String content = "Dear [[name]],<br>"
	            + "Please click the link below to verify your registration:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	            + "Thank you,<br>"
	            + "Your company name.";
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom(fromAddress, senderName);
	    helper.setTo(toAddress);
	    helper.setSubject(subject);
	     
	    content = content.replace("[[name]]", user.getFirstName());
	    String verifyURL = siteURL + "/api/auth/verify?code=" + user.getVerificationCode();
	     
	    content = content.replace("[[URL]]", verifyURL);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
	     
	}

	@Override
	public boolean verify(String code) {
		User user = userDao.findByVerification(code);
		
		 if (user == null || user.isEnabled()) {
		        return false;
		    } else {
		        user.setVerificationCode(null);
		        user.setEnabled(true);
		        userDao.save(user);
		         
		        return true;
		    }

	}
}
