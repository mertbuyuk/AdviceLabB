package com.mb.security.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.mb.jwt.JwtUtil;
import com.mb.services.abstracts.UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Autowired
	JwtAuthEntryPoint jwtAuthEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// cors başkalarının veri çalmasını engellemek için tarayıcının uyguladığı sert
		// kuralları esnetmek için kullanılır
		// başka sitenin senden veri çekmesi vs
       http.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/h2-ui/**","/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**","/api/auth/**","/verify").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and().sessionManagement()
				
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
        http.headers().frameOptions().sameOrigin();
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public JwtUtil jwtUTil() throws Exception {
		return new JwtUtil();
	}
	

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public JwtTokenFilter tokenFilter() throws Exception {
		return new JwtTokenFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
