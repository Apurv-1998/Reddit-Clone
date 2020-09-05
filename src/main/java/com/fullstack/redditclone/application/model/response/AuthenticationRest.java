package com.fullstack.redditclone.application.model.response;

import lombok.Data;

@Data
public class AuthenticationRest {
	
	private String authenticationToken;
	private String userName;
	private String userId;

}
