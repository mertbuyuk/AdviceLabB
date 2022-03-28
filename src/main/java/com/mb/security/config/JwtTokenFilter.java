package com.mb.security.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mb.dao.UserDao;
import com.mb.jwt.JwtUtil;
import com.mb.services.abstracts.UserService;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	UserService userDao;
	
	@Autowired
	private  JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String header = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		
		System.out.println("dofilter" + header);
		
		if (header == null || !header.startsWith("Bearer ")) {
			
			System.out.println("dofilterssssssss" + header);
			filterChain.doFilter(request, response);
			return;
		}
		
		final String token = header.split(" ")[1].trim();
		
		UserDetails userDetails = userDao
		            .loadUserByUsername(jwtUtil.extractUsermame(token));
		
		if(!jwtUtil.validateToken(token,userDetails )) {
			filterChain.doFilter(request, response);
			return;
		}
		
		 UsernamePasswordAuthenticationToken
         authentication = new UsernamePasswordAuthenticationToken(
             userDetails, null,
             userDetails == null ?
                 List.of() : userDetails.getAuthorities()
         );
		 
		 authentication.setDetails(
		            new WebAuthenticationDetailsSource().buildDetails(request)
		        );

		        SecurityContextHolder.getContext().setAuthentication(authentication);
		        filterChain.doFilter(request, response);
	}

}
