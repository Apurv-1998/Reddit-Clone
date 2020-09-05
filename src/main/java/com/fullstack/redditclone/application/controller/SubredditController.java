package com.fullstack.redditclone.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.redditclone.application.dto.CreateSubredditDto;
import com.fullstack.redditclone.application.entity.SubredditEntity;
import com.fullstack.redditclone.application.model.request.CreateSubredditDetailsModel;
import com.fullstack.redditclone.application.model.response.SubredditRest;
import com.fullstack.redditclone.application.model.response.UserIdRest;
import com.fullstack.redditclone.application.service.SubredditService;

@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	SubredditService subredditService;
	
	@PostMapping(path = "/{userId}/createSubreddits",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<SubredditRest> createSubReddit(@PathVariable String userId,@RequestBody CreateSubredditDetailsModel createSubredditDetailsModel) {
		
		CreateSubredditDto subredditDto = mapper.map(createSubredditDetailsModel,CreateSubredditDto.class);
		
		SubredditEntity subreddit = subredditService.createNewSubreddit(userId,subredditDto);
		
		SubredditRest response = mapper.map(subreddit,SubredditRest.class);
		
		Link link = WebMvcLinkBuilder.linkTo(UserController.class).slash("users").slash(subreddit.getUserDetails().getUserId()).withRel("Account user Details");
		
		response.add(link);
		
		ResponseEntity<SubredditRest> restEntity = new ResponseEntity<>(response,HttpStatus.CREATED);
		
		return restEntity;
	}

	
	@GetMapping(path = "/showAll",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<SubredditRest> allSubreddits() {
		
		List<SubredditEntity> subreddits = subredditService.showAllSubReddits();
		
		List<SubredditRest> response = new ArrayList<>();
		
		for(SubredditEntity subreddit:subreddits) {
			
			SubredditRest rest = mapper.map(subreddit,SubredditRest.class);
			
			Link link = WebMvcLinkBuilder.linkTo(UserController.class).slash("users").slash(subreddit.getUserDetails().getUserId()).withRel("Account user Details");
			
			rest.add(link);
			
			rest.setUserId(subreddit.getUserDetails().getUserId());
			
			rest.setPostCount(subreddit.getPostDetails().size());
			
			response.add(rest);
		}
		
		return response;
		
	}
	
	
	//Getting the UserId
	
	@GetMapping(path = "/getUserId",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public UserIdRest getUserSubreddits() {
		
		List<SubredditEntity> subreddits = subredditService.showAllSubReddits();
		
		UserIdRest response = new UserIdRest();
		
		response.setUserId(subreddits.get(0).getUserDetails().getUserId());
		
		return response;
		
	}
	
}
