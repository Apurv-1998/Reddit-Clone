package com.fullstack.redditclone.application.model.response;

import org.springframework.hateoas.RepresentationModel;

public class CommentRest extends RepresentationModel<CommentRest> {

	private String commentId;
	private String text;
	private String duration;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
