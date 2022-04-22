package com.mb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mb.demo.model.Message;
import com.mb.demo.model.User;
import com.mb.demo.responses.Response;
import com.mb.services.concretes.UserManager;

@RestController
@RequestMapping("/api/auth/")
public class SignUpController {

	@Autowired 
	UserManager userManager;
	
	@PostMapping("process")
	public ResponseEntity<?> processRegister(@RequestBody User user, HttpServletRequest httpServletRequest) {
	
		Message message = userManager.register(user, getSiteURL(httpServletRequest));
		Response response = new Response();
		response.setStatus(message.getCode());
		response.setResponseBody(message.getMessage());
		return response.build();
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
