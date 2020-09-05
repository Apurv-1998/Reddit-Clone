package com.fullstack.redditclone.application.model.request;

import lombok.Data;

@Data
public class LoginRequestDetailsModel {
	
	private String firstName;
	private String lastName;
	private String password;

}
