package com.fullstack.redditclone.application.model.response;

import org.springframework.hateoas.RepresentationModel;

public class SubredditRest extends RepresentationModel<SubredditRest> {

	private String subredditId;
	private String subredditName;
	private String description;
	private String userId;
	private int postCount;

	public String getSubredditId() {
		return subredditId;
	}

	public void setSubredditId(String subredditId) {
		this.subredditId = subredditId;
	}

	public String getSubredditName() {
		return subredditName;
	}

	public void setSubredditName(String subredditName) {
		this.subredditName = subredditName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

}
