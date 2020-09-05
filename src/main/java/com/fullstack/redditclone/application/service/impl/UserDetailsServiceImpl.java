package com.fullstack.redditclone.application.service.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.exception.SpringRedditException;
import com.fullstack.redditclone.application.repository.UserRepository;
import com.fullstack.redditclone.application.shared.Utils;

import static java.util.Collections.singletonList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	Utils utils;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		String[] str = utils.filterUserName(userName);
		
		if(str.length<2 || str.length>2)
			throw new SpringRedditException("Invalid UserName");
		
		String firstName = str[0];
		String lastName = str[1];
		
		System.out.println(firstName+" "+lastName);
		
		UserEntity user = userRepository.findUserByFirstNameAndLastName(firstName,lastName);
		
		System.out.println("Got "+user);
		
		if(user == null)
			throw new SpringRedditException("User Doesnot Exist");
		
		return new User(firstName+" "+lastName, user.getRegisteredPassword(), user.isVerified(),
					    true,true,true, getAuthorities("USER"));
		
		
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(String role){
		return singletonList(new SimpleGrantedAuthority(role));
	}

}
