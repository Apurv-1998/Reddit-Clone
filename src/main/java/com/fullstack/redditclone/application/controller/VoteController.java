package com.fullstack.redditclone.application.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.redditclone.application.dto.CreateVoteDto;
import com.fullstack.redditclone.application.model.request.CreateVoteRequestDetailsModel;
import com.fullstack.redditclone.application.model.response.VoteRest;
import com.fullstack.redditclone.application.service.VoteService;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	VoteService voteService;
	
	
	@PostMapping(path = "/{postId}/votePost",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<VoteRest> addPosts(@PathVariable String postId,@RequestBody CreateVoteRequestDetailsModel createVoteRequestDetailsModel) {
		
		
		CreateVoteDto voteDto = mapper.map(createVoteRequestDetailsModel,CreateVoteDto.class);
		
		VoteRest response = voteService.castVote(postId,voteDto);
		
		ResponseEntity<VoteRest> returnValue = new ResponseEntity<>(response,HttpStatus.OK);
		
		return returnValue;
				
	}

}
