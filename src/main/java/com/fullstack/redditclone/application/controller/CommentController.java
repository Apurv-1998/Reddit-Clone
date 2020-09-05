package com.fullstack.redditclone.application.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.redditclone.application.dto.CreateCommentDto;
import com.fullstack.redditclone.application.entity.CommentEntity;
import com.fullstack.redditclone.application.model.request.CreateCommentDetailsModel;
import com.fullstack.redditclone.application.model.response.CommentRest;
import com.fullstack.redditclone.application.service.CommentService;
import com.fullstack.redditclone.application.shared.Utils;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	Utils utils;
	
	
	
	@PostMapping(path = "/{userId}/{postId}/createComments",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CommentRest> createComments(@PathVariable String userId,@PathVariable String postId,@RequestBody CreateCommentDetailsModel createCommentDetailsModel) {
		
		CreateCommentDto commentDto = mapper.map(createCommentDetailsModel,CreateCommentDto.class);
		
		 
		CommentEntity comment = commentService.createComment(userId,postId,commentDto);
		
		CommentRest response = mapper.map(comment,CommentRest.class);
		
		Link postLink = WebMvcLinkBuilder.linkTo(PostController.class).slash(comment.getPostDetails().getPostId()).withRel("Comments Post Details");
		Link subredditLink = WebMvcLinkBuilder.linkTo(SubredditController.class).slash(comment.getPostDetails().getSubredditDetails().getSubredditId()).withRel("Comments Subreddit Details");
		Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("Comments User Details");
		
		
		
		response.add(postLink);
		response.add(subredditLink);
		response.add(userLink);
		response.setDuration(utils.GetDuration(comment));
		
		ResponseEntity<CommentRest> returnValue = new ResponseEntity<>(response,HttpStatus.CREATED);
		
		
		return returnValue;
	}

}
