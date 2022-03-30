package com.mb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.User;
import com.mb.services.concretes.UserManager;

@RestController
@RequestMapping("/api/auth/")
public class SignUpController {

	@Autowired 
	UserManager userManager;
	
	@PostMapping("process")
	public String processRegister(@RequestBody User user, HttpServletRequest httpServletRequest) {
		
		String message = userManager.register(user, getSiteURL(httpServletRequest));
		return message;
	}
	
	 private String getSiteURL(HttpServletRequest request) {
	        String siteURL = request.getRequestURL().toString();
	        return siteURL.replace(request.getServletPath(), "");
	    } 
	 

	 @GetMapping("verify")
	 public String verifyUser(@Param("code") String code) {
	     if (userManager.verify(code)) {
	         return "verify_success";
	     } else {
	         return "verify_fail";
	     }
	 }
	    
}
