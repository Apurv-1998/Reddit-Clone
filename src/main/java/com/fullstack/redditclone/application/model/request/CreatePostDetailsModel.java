package com.fullstack.redditclone.application.model.request;

import lombok.Data;

@Data
public class CreatePostDetailsModel {
	
	private String subredditName;
	private String postName;
	private String description;
}
