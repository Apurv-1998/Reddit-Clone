package com.fullstack.redditclone.application.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.dto.CreateSubredditDto;
import com.fullstack.redditclone.application.entity.PostEntity;
import com.fullstack.redditclone.application.entity.SubredditEntity;
import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.exception.SpringRedditException;
import com.fullstack.redditclone.application.model.response.SubredditRest;
import com.fullstack.redditclone.application.repository.SubredditRepository;
import com.fullstack.redditclone.application.repository.UserRepository;
import com.fullstack.redditclone.application.service.SubredditService;
import com.fullstack.redditclone.application.service.UserService;
import com.fullstack.redditclone.application.shared.Utils;




@Service
public class SubredditServiceImpl implements SubredditService {
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SubredditRepository subredditRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	Utils utils;

	@Override
	@Transactional
	public SubredditEntity createNewSubreddit(String userId,CreateSubredditDto subredditDto) {
		
		//Check whether userId is correct or not
		
		UserEntity user = userRepository.findUserByUserId(userId);
		
		if(user == null)
			throw new SpringRedditException("UserId is invalid");
		
		SubredditEntity subreddit = mapper.map(subredditDto, SubredditEntity.class);
		
		subreddit.setSubredditId(utils.CreateSubredditId(5));
		subreddit.setUserDetails(user);
		subreddit.setSubredditName(utils.ManageSubredditName(subreddit.getSubredditName()));
		
		SubredditEntity saved = subredditRepository.save(subreddit);
		
		
		if(user.getSubredditDetails() == null) {
			List<SubredditEntity> subreddits = new ArrayList<>();
			subreddits.add(saved);
			user.setSubredditDetails(subreddits);
		}
		else {
			List<SubredditEntity> subreddits = user.getSubredditDetails();
			subreddits.add(saved);
			user.setSubredditDetails(subreddits);
		}
		
		userRepository.save(user);
		
		
		return saved;
		
	}

	@Override
	public List<SubredditEntity> showAllSubReddits() {
		
		Iterable<SubredditEntity> subreddits = subredditRepository.findAll();
		
		Iterator<SubredditEntity> it = subreddits.iterator();
		
		List<SubredditEntity> response = new ArrayList<>();
		
		while(it.hasNext())
			response.add(it.next());
		
		return response;
		
	}

	@Override
	public SubredditEntity savePost(PostEntity savedPost,SubredditEntity subreddit) {
		
		if(subreddit.getPostDetails() == null) {
			List<PostEntity> posts = new ArrayList<>();
			posts.add(savedPost);
			subreddit.setPostDetails(posts);
		}
		else {
			List<PostEntity> posts = subreddit.getPostDetails();
			posts.add(savedPost);
			subreddit.setPostDetails(posts);
		}
		
		return subredditRepository.save(subreddit);
		
	}

	@Override
	public SubredditRest getUserSubreddits() {
		
		
		String userName = userService.getCurrentUser();
		
		UserEntity user =  userService.getUserFromUserName(userName);
		
		SubredditRest response = mapper.map(user.getSubredditDetails(),SubredditRest.class);
		
		response.setPostCount(utils.getPostCount(user.getSubredditDetails()));
		response.setUserId(user.getUserId());
		
		return response;
		
	}

}
