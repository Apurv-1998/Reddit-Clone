package com.fullstack.redditclone.application.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.dto.CreateVoteDto;
import com.fullstack.redditclone.application.entity.PostEntity;
import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.entity.VoteEntity;
import com.fullstack.redditclone.application.exception.SpringRedditException;
import com.fullstack.redditclone.application.model.response.PostRest;
import com.fullstack.redditclone.application.model.response.UserRest;
import com.fullstack.redditclone.application.model.response.VoteRest;
import com.fullstack.redditclone.application.repository.PostRepository;
import com.fullstack.redditclone.application.repository.UserRepository;
import com.fullstack.redditclone.application.repository.VoteRepository;
import com.fullstack.redditclone.application.service.PostService;
import com.fullstack.redditclone.application.service.UserService;
import com.fullstack.redditclone.application.service.VoteService;
import com.fullstack.redditclone.application.shared.Utils;


@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	VoteRepository voteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	Utils utils;
	
	
	@Transactional
	@Override
	public VoteRest castVote(String postId, CreateVoteDto voteDto) {
		
		System.out.println(voteDto.getVoteType().name());
		
		PostEntity post = postRepository.findPostByPostId(postId);
		
		if(post == null)
			throw new SpringRedditException("The mentioned post doesnot exist");
		
		
		String userName = userService.getCurrentUser();
		
		UserEntity user = userService.getUserFromUserName(userName);
		
		//If the vote in the votes done By the user is present in the vote received by the post and is of the same type then user cannot vote
		
		if(post.getVoteDetails()!=null && user.getVoteDetails()!=null) {
			
			List<VoteEntity> postVotes = post.getVoteDetails();
			List<VoteEntity> userVotes = user.getVoteDetails();
			
			for(VoteEntity postVote : postVotes) {
				for(VoteEntity userVote:userVotes) {
					System.out.println(postVote.getVoteType().name());
					if(postVote.getVoteId().equals(userVote.getVoteId()) && voteDto.getVoteType().name().equals(userVote.getVoteType().name()) && userVote.getLastAction().name().equals(voteDto.getVoteType().name())) {
						throw new SpringRedditException("The Post Is Already Voted By You");
					}
				}
			}
			
		}
		
		
		
		VoteEntity vote = mapper.map(voteDto,VoteEntity.class);
		
		vote.setVoteId(utils.GenerateCommentId(6));
		vote.setLastAction(voteDto.getVoteType());
		vote.setPostDetails(post);
		vote.setUserDetails(user);
		
		VoteEntity savedVote = voteRepository.save(vote);
		
		PostEntity savedPost = postService.saveVoteAndCount(savedVote,post);
		userService.updateUserPost(savedPost, post.getUserDetails());
		
		
		
		UserEntity savedUser = userService.saveVoteToUser(savedVote,user);
		
		VoteRest response = new VoteRest();
		
		response.setVoteId(savedVote.getVoteId());
		response.setVoteType(savedVote.getVoteType());
		response.setUserDetails(mapper.map(savedUser, UserRest.class));
		response.setPostDetails(mapper.map(savedPost, PostRest.class));
		response.setDuration(utils.GetDuration(savedPost));
		
		return response;
		
		
	}

}
