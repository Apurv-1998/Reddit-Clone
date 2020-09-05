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

import com.fullstack.redditclone.application.dto.CreatePostDto;
import com.fullstack.redditclone.application.entity.PostEntity;
import com.fullstack.redditclone.application.model.request.CreatePostDetailsModel;
import com.fullstack.redditclone.application.model.response.PostRest;
import com.fullstack.redditclone.application.service.PostService;
import com.fullstack.redditclone.application.shared.Utils;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	PostService postService;
	
	@Autowired
	Utils utils;
	
	@PostMapping(path = "/createPosts",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<PostRest> createPosts(@RequestBody CreatePostDetailsModel createPostDetailsModel) {
		
		CreatePostDto postDto = mapper.map(createPostDetailsModel,CreatePostDto.class);
		
		PostEntity post = postService.createNewPost(postDto);
		
		PostRest response = mapper.map(post,PostRest.class);
		
		Link subredditLink = WebMvcLinkBuilder.linkTo(SubredditController.class).slash(post.getSubredditDetails().getSubredditId()).withRel("Post's Subreddit Detials");
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(post.getUserDetails().getUserId()).withRel("Post's User Detials");
		
		response.add(subredditLink);
		response.add(userLink);
		
		ResponseEntity<PostRest> returnValue = new ResponseEntity<>(response,HttpStatus.CREATED);
		
		return returnValue;
		
		
	}
	
	
	
	@GetMapping(path="/allPosts",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<PostRest> getAllPosts() {
		
		List<PostEntity> posts =  postService.getAllPosts();
		
		List<PostRest> response = new ArrayList<>();
		
		for(PostEntity post:posts) {
			PostRest rest = mapper.map(post,PostRest.class);
			
			Link subredditLink = WebMvcLinkBuilder.linkTo(SubredditController.class).slash(post.getSubredditDetails().getSubredditId()).withRel("Post's Subreddit Detials");
			Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(post.getUserDetails().getUserId()).withRel("Post's User Detials");
			
			rest.add(subredditLink);
			rest.add(userLink);
			
			rest.setDuration(utils.GetDuration(post));
			
			response.add(rest);
			
			
		}
		
		return response;
		
	}
	
	
	@GetMapping(path = "/{userId}/showPosts",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<PostRest> getUserPosts(@PathVariable String userId) {
		
		List<PostRest> response = postService.getUserPosts(userId);
		
		for(int i=0;i<response.size();i++) {
			
			PostRest rest = response.get(i);
			
			Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("Post's User Detials");
			
			rest.add(userLink);
			rest.setDuration(utils.GetDuration(postService.getPostFromPostId(rest.getPostId())));
			
			response.set(i, rest);
		}
		
		return response;
		
	}
	
	
	@GetMapping(path = "/{postId}/displayPost",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<PostRest> showPost(@PathVariable String postId) {
		
		PostRest response = postService.getParticularPostDetails(postId);
		
		return new ResponseEntity<PostRest>(response,HttpStatus.OK);
		
	}

}
