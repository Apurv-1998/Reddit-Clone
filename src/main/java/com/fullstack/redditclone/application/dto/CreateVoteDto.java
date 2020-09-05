package com.fullstack.redditclone.application.dto;

import java.io.Serializable;

import com.fullstack.redditclone.application.shared.VoteType;

import lombok.Data;

@Data
public class CreateVoteDto implements Serializable {
	
	
	
	private static final long serialVersionUID = -8826778000899919410L;
	
	
	private String voteId;
	private VoteType voteType;
	
	
	
}
