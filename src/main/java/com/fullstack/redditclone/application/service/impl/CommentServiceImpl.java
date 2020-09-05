package com.fullstack.redditclone.application.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.dto.CreateCommentDto;
import com.fullstack.redditclone.application.entity.CommentEntity;
import com.fullstack.redditclone.application.entity.PostEntity;
import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.exception.SpringRedditException;
import com.fullstack.redditclone.application.repository.CommentRepository;
import com.fullstack.redditclone.application.repository.PostRepository;
import com.fullstack.redditclone.application.repository.UserRepository;
import com.fullstack.redditclone.application.service.CommentService;
import com.fullstack.redditclone.application.service.MailService;
import com.fullstack.redditclone.application.service.PostService;
import com.fullstack.redditclone.application.service.UserService;
import com.fullstack.redditclone.application.shared.EmailBody;
import com.fullstack.redditclone.application.shared.Utils;


@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	Utils utils;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	MailService mailSerivce;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public CommentEntity createComment(String userId,String postId,CreateCommentDto commentDto) {
		
		//Checking the postId
		
		PostEntity post = postRepository.findPostByPostId(postId);
		
		if(post == null)
			throw new SpringRedditException("Invalid PostId");
		
		//Getting User Details From Post
		
		UserEntity user = userRepository.findUserByUserId(userId);
		
		if(user == null)
			throw new SpringRedditException("Invalid User");
		
		
		CommentEntity comment = mapper.map(commentDto,CommentEntity.class);
		
		comment.setCommentId(utils.GenerateCommentId(5));
		comment.setPostDetails(post);
		comment.setUserDetails(user);
		
		CommentEntity savedComment = commentRepository.save(comment);
		
		
		//Saving Post And Updating User
		PostEntity savedPost = postService.saveComment(post,savedComment);
		userService.updateUserPost(savedPost,post.getUserDetails());
		
		System.out.println(post.getUserDetails().getUserId()+" "+userId);
		
		if(!post.getUserDetails().getUserId().equals(userId)) {
			//Saving User
			System.out.println("Entering Here");
			userService.savePostAndComment(user,savedComment);
		}
		
		
		//Sending notification mail to user
		mailSerivce.sendMail(new EmailBody("New Comment On Post "+savedPost.getPostName(),savedPost.getUserDetails().getEmail(),"New Comment On Ypur Post By -> "+user.getFirstName()+" "+user.getLastName()));
		
		
		
		return savedComment;
		
		
		
	}

}
