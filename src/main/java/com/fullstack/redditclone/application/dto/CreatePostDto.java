package com.fullstack.redditclone.application.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreatePostDto implements Serializable {

	private static final long serialVersionUID = 6328383684231337381L;
	
	private String postId;
	private String subredditName;
	private String postName;
	private String description;
	private String postUrl;

}
