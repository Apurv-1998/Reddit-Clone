package com.fullstack.redditclone.application.model.response;

import com.fullstack.redditclone.application.shared.VoteType;

import lombok.Data;

@Data
public class VoteRest {

	private String voteId;
	private VoteType voteType;
	private UserRest userDetails;
	private PostRest postDetails;
	private String duration;

}
