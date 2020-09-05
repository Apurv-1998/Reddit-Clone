package com.fullstack.redditclone.application.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.redditclone.application.dto.CreateUserDto;
import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.model.request.CreateUserDetailsModel;
import com.fullstack.redditclone.application.model.request.LoginRequestDetailsModel;
import com.fullstack.redditclone.application.model.response.AuthenticationRest;
import com.fullstack.redditclone.application.model.response.UserRest;
import com.fullstack.redditclone.application.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	UserService userService;
	
	
	@PostMapping(path = "/users/signup",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserRest> signupUser(@RequestBody CreateUserDetailsModel createUserDetailsModel) {
		
		CreateUserDto userDto = mapper.map(createUserDetailsModel,CreateUserDto.class);
		
		UserEntity user = userService.signupNewUser(userDto);
		
		UserRest response =  mapper.map(user,UserRest.class);
		
		ResponseEntity<UserRest> returnValue = new ResponseEntity<>(response,HttpStatus.CREATED);
		
		return returnValue;
		
	}
	
	// Account Verification
	
	@GetMapping(path = "accountVerification/{tokenId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<String> accountVerification(@PathVariable String tokenId) {
		
		String responseMessage = "";
		
		if(userService.verifyUserAccount(tokenId))
			responseMessage = "Account Verification Successful";
		else
			responseMessage = "Account Cannot Be Verified";
		
		
		return new ResponseEntity<String>(responseMessage,HttpStatus.OK);
		
	}
	
	
	/*
	 * User Login
	 * 
	 * AuthService -> Authentication Manager(Security Config) -> User Details Service(Built-in Spring Security core) : loadUserByUserName()Verifies User-> Database
	 * 
	 * Request Body -> UserName and Password
	 * */
	
	@PostMapping(path = "/login",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public AuthenticationRest signinUser(@RequestBody LoginRequestDetailsModel loginDetailsModel) {
		
		return userService.loginUser(loginDetailsModel);
		
	}

}
