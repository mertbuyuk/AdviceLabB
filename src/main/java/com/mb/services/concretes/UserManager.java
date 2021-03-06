package com.mb.services.concretes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.mb.dao.RelationDao;
import com.mb.dao.UserDao;
import com.mb.demo.dtos.RelationDto;
import com.mb.demo.dtos.UserDto;
import com.mb.demo.model.Followers;
import com.mb.demo.model.Message;
import com.mb.demo.model.Post;
import com.mb.demo.model.Relation;
import com.mb.demo.model.User;
import com.mb.services.abstracts.UserService;
import com.mb.utils.FileUploadUtil;
import com.mb.utils.ImageUtility;

import net.bytebuddy.utility.RandomString;

@Service
public class UserManager implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	RelationDao relationDao;

	@Autowired
	EntityManager entityManager;

	@Override
	public User findById(Long id) {

		return userDao.getById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getUserByFirstName(username);
		if (user == null) {
			throw new UsernameNotFoundException("Not found");
		}

		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		/*
		 * user.getRoles().forEach(role -> { authorities.add(new
		 * SimpleGrantedAuthority(role.getRoleName())); });
		 */

		return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(),
				authorities);
	}

	@Override
	public Message register(User user, String siteUrl) {
		Message message = new Message("");
		// This class return type will change to json
		if (userDao.existsByEmail(user.getEmail()) || userDao.existsByFirstName(user.getFirstName())) {
			message.setMessage("This email/nickname exist");
			message.setCode(HttpStatus.CONFLICT);
			return message;
		}

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
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		message.setCode(HttpStatus.OK);
		message.setMessage("Please verify your email");

		return message;
	}

	private void sendVerificationEmail(User user, String siteURL)
			throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getEmail();
		String fromAddress = "mertbuyuk.e@gmail.com";
		String senderName = "M Tech.";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "M Tech.";

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

	@Override
	public User isEnabledUser(String username) {
		User user = userDao.getUserByFirstName(username);
		return user;
	}

	@Override
	public User addPostToUser(Long id, Post post) {
		User user = findById(id);

		if (user == null) {
			return null;
		}

		user.getPost().add(post);
		userDao.save(user);
		return user;
	}

	@Override
	public List<Post> getUsersPost(Long id) {

		return userDao.getById(id).getPost();
	}

	@Override
	public List<User> getUsersFollowers(Long id) {

		List<Followers> users = relationDao.getUserFollowerId(id);

		return users.stream().map(this::findFollowers).collect(Collectors.toList());
	}

	@Override
	public List<User> getUsersFollowed(Long id) {

		List<Followers> users = relationDao.getUserFollowedId(id);

		return users.stream().map(this::findFolloweds).collect(Collectors.toList());
	}

	private User findFolloweds(Followers user) {
		User followedUser = userDao.getById(user.getTo().getId());
		return followedUser;
	}

	private User findFollowers(Followers user) {
		User followedUser = userDao.getById(user.getFrom().getId());
		return followedUser;
	}

	@Override
	public void followById(Long fromId, Long toId) {

		User fromUser = userDao.getById(fromId);
		User toUser = userDao.getById(toId);

		Followers followers = new Followers(fromUser, toUser);

		relationDao.save(followers);
	}

	@Override
	public void deletebyId(Long fromId, Long toId) {
		Followers followers = relationDao.deleteRelation(fromId, toId);
		relationDao.deleteById(followers.getId());
	}

	@Override
	public void saveUserPhoto(Long id, MultipartFile file) throws IOException {

		User user = userDao.getById(id);
		
		user.setPhoto(file.getBytes());

		User savedUser = userDao.save(user);

		//String uploadDir = "user-photos/" + savedUser.getId();

		//FileUploadUtil.saveFile(uploadDir, fileName, file);
	}

	@Override
	public byte[] getUserPhoto(Long id) {
		if (id == null)
			return null;
		
		User user = userDao.getById(id);
		
		if (user == null) 
			return null;
		

		System.out.println(user.getFirstName());
		return user.getPhoto();
	}

	@Override
	public Relation getCountOfRelations(Long id) {

		Relation relation = new Relation();
		relation.setFollower(relationDao.getCountofFollower(id));
		relation.setFollowing(relationDao.getCountofFollowing(id));

		return relation;
	}

	@Override
	public List<User> searchByName(String username) {

		List<User> users = userDao.searchUser(username);

		return users;
	}

}
