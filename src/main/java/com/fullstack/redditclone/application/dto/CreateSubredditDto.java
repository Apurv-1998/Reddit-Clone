package com.fullstack.redditclone.application.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateSubredditDto implements Serializable {

	private static final long serialVersionUID = 7035538327326893733L;
	
	private String subredditId;
	private String subredditName;
	private String description;

}
