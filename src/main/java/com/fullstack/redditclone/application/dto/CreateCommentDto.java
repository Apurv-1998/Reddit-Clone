package com.fullstack.redditclone.application.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateCommentDto implements Serializable {

	private static final long serialVersionUID = 8147204455486742539L;
	
	private String commentId;
	private String text;

}
