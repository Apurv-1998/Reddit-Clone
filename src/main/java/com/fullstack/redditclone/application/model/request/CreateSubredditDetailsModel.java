package com.fullstack.redditclone.application.model.request;

import lombok.Data;

@Data
public class CreateSubredditDetailsModel {
	
	private String subredditName;
	private String description;

}
