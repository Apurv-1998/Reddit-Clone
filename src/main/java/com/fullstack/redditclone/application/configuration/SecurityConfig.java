package com.fullstack.redditclone.application.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fullstack.redditclone.application.security.JWTAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JWTAuthenticationFilter authenticationFilter;
	
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	//Prevent CSRF Attacks
	@Override
	public void configure(HttpSecurity httpSecurity ) throws Exception {
		
		httpSecurity.csrf().disable()
		                   .authorizeRequests()
		                   .antMatchers("/api/**").permitAll()
		                   .antMatchers(HttpMethod.POST, "/api/subreddits/**").permitAll()
		                   .anyRequest()
		                   .authenticated();
		
		httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	//Creating an Authentication manager
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationmanagerBuilder) throws Exception {
		
		authenticationmanagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
