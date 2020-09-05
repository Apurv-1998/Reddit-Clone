package com.fullstack.redditclone.application.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.dto.CreatePostDto;
import com.fullstack.redditclone.application.entity.CommentEntity;
import com.fullstack.redditclone.application.entity.PostEntity;
import com.fullstack.redditclone.application.entity.SubredditEntity;
import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.entity.VoteEntity;
import com.fullstack.redditclone.application.exception.SpringRedditException;
import com.fullstack.redditclone.application.model.response.PostRest;
import com.fullstack.redditclone.application.repository.PostRepository;
import com.fullstack.redditclone.application.repository.SubredditRepository;
import com.fullstack.redditclone.application.service.PostService;
import com.fullstack.redditclone.application.service.SubredditService;
import com.fullstack.redditclone.application.service.UserService;
import com.fullstack.redditclone.application.shared.UrlEnum;
import com.fullstack.redditclone.application.shared.Utils;


@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	SubredditRepository subredditRepository;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	Utils utils;
	
	@Autowired
	SubredditService subredditService;
	
	@Autowired
	UserService userService;
	
	@Override
	public PostEntity createNewPost(CreatePostDto postDto) {
		
		//Checking Subreddit
		
		SubredditEntity subreddit = subredditRepository.findSubredditBySubredditName(postDto.getSubredditName());
		
		if(subreddit == null)
			throw new SpringRedditException("Invalid Subreddit Id");
		
		UserEntity user = subreddit.getUserDetails();
		
		
		PostEntity post = mapper.map(postDto,PostEntity.class);
		
		post.setPostId(utils.CreateSubredditId(5));
		post.setPostUrl(UrlEnum.POST_BASE_URL.getMessage()+post.getPostId());
		post.setSubredditDetails(subreddit);
		post.setUserDetails(user);
		
		
		PostEntity savedPost = postRepository.save(post);
		
		
		SubredditEntity savedSubreddit = subredditService.savePost(savedPost,subreddit);
		
		
		userService.savePostAndSubreddit(savedPost,savedSubreddit,user);
		
		
		
		return savedPost;
	}
	
	
	

	@Override
	public PostEntity saveComment(PostEntity post, CommentEntity savedComment) {
		
		if(post.getCommentDetails() == null) {
			List<CommentEntity> comments = new ArrayList<>();
			comments.add(savedComment);
			post.setCommentDetails(comments);
		}
		else {
			List<CommentEntity> comments = post.getCommentDetails();
			comments.add(savedComment);
			post.setCommentDetails(comments);
		}
		
		return postRepository.save(post);
	}




	@Override
	public PostEntity saveVoteAndCount(VoteEntity savedVote, PostEntity post) {
		
		if(savedVote.getVoteType().name().equals("UPVOTE")) {
			int voteCount = post.getVoteCount()+1;
			post.setVoteCount(voteCount);
		}
		else {
			int voteCount = post.getVoteCount()-1;
			post.setVoteCount(voteCount);
		}
		
		if(post.getVoteDetails() == null) {
			List<VoteEntity> votes = new ArrayList<>();
			votes.add(savedVote);
			post.setVoteDetails(votes);
		}
		else {
			List<VoteEntity> votes = post.getVoteDetails();
			votes.add(savedVote);
			post.setVoteDetails(votes);
		}
		
		return postRepository.save(post);
	}




	@Override
	public List<PostEntity> getAllPosts() {
		
		Iterable<PostEntity> allPosts =  postRepository.findAll();
		
		Iterator<PostEntity> it = allPosts.iterator();
		
		List<PostEntity> posts = new ArrayList<>();
		
		while(it.hasNext()) {
			posts.add(it.next());
		}
		
		return posts;
		
	}




	@Override
	public List<PostRest> getUserPosts(String userId) {
		
		List<PostRest> returnValue = new ArrayList<>();
		
		
		UserEntity user = userService.getUserFromUserId(userId);
		
		List<PostEntity> posts = user.getPostDetails();
		
		for(PostEntity post:posts) {
			PostRest rest = mapper.map(post, PostRest.class);
			rest.setSubredditName(post.getSubredditDetails().getSubredditName());
			rest.setUserName(post.getUserDetails().getFirstName()+" "+post.getUserDetails().getLastName());
			rest.setCommentCount(post.getCommentDetails().size());
			returnValue.add(rest);
		}
		
		return returnValue;
		
	}




	@Override
	public PostEntity getPostFromPostId(String postId) {
		
		return postRepository.findPostByPostId(postId);
	}




	@Override
	public PostRest getParticularPostDetails(String postId) {
		
		PostEntity post = postRepository.findPostByPostId(postId);
		
		if(post == null)
			throw new SpringRedditException("The Post Doesnot exist");
		
		PostRest response = mapper.map(post,PostRest.class);
		
		response.setCommentCount(post.getCommentDetails().size());
		response.setSubredditName(post.getSubredditDetails().getSubredditName());
		response.setUserName(post.getUserDetails().getFirstName()+" "+post.getUserDetails().getLastName());
		response.setVoteCount(post.getVoteCount());
		response.setDuration(utils.GetDuration(post));
		response.setUserId(post.getUserDetails().getUserId());
		
		return response;
		
	}

	

}
