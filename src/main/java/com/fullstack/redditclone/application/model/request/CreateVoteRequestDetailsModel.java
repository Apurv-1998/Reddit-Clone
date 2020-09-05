package com.fullstack.redditclone.application.model.request;

import com.fullstack.redditclone.application.shared.VoteType;

import lombok.Data;

@Data
public class CreateVoteRequestDetailsModel {
	
	private VoteType voteType;

}
