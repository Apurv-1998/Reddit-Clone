package com.fullstack.redditclone.application.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.dto.CreateUserDto;
import com.fullstack.redditclone.application.entity.CommentEntity;
import com.fullstack.redditclone.application.entity.PostEntity;
import com.fullstack.redditclone.application.entity.SubredditEntity;
import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.entity.VerificationTokenEntity;
import com.fullstack.redditclone.application.entity.VoteEntity;
import com.fullstack.redditclone.application.exception.SpringRedditException;
import com.fullstack.redditclone.application.model.request.LoginRequestDetailsModel;
import com.fullstack.redditclone.application.model.response.AuthenticationRest;
import com.fullstack.redditclone.application.repository.UserRepository;
import com.fullstack.redditclone.application.repository.VerificationTokenRepository;
import com.fullstack.redditclone.application.security.JWTProvider;
import com.fullstack.redditclone.application.service.MailService;
import com.fullstack.redditclone.application.service.TokenService;
import com.fullstack.redditclone.application.service.UserService;
import com.fullstack.redditclone.application.shared.EmailBody;
import com.fullstack.redditclone.application.shared.Utils;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	Utils utils;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	VerificationTokenRepository tokenRepository;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	JWTProvider tokenProvider;
	
	@Override
	@Transactional
	public UserEntity signupNewUser(CreateUserDto userDto) {
		
		UserEntity entity = mapper.map(userDto,UserEntity.class);
		
		entity.setUserId(utils.GenerateUserId(10));
		entity.setRegisteredPassword(passwordEncoder.encode(entity.getPassword()));
		
		UserEntity saved = userRepository.save(entity);
		
		
		//Generate the email-verification token after saving the user in the database
		
		String verificationToken = tokenService.createVerificationToken();
		
		VerificationTokenEntity tokenEntity = new VerificationTokenEntity();
		tokenEntity.setTokenId(verificationToken);
		tokenEntity.setUserDetails(saved);
		VerificationTokenEntity savedToken =  tokenRepository.save(tokenEntity);
		
		//Sending the email to the user -> Thymeleaf Template
		
		mailService.sendMail(new EmailBody("Please Activate Your Account",saved.getEmail(),
				                            "Thank you for signing to spring reddit please click on the url to activate your account : "
				                            + " http://localhost:8080/reddit-clone-app/api/accountVerification/"+savedToken.getTokenId())); //passing the token as the path variable
		
		
		return saved;
		
	}

	@Override
	@Transactional
	public boolean verifyUserAccount(String tokenId) {
		
		UserEntity userDetails = tokenService.getUserForToken(tokenId);
		
		if(userDetails == null)
			throw new SpringRedditException("User DoesNot Exist");
		
		userDetails.setVerified(true);
		
		UserEntity saved = userRepository.save(userDetails);
		
		return saved != null;
		
	}

	@Override
	@Transactional
	public AuthenticationRest loginUser(LoginRequestDetailsModel loginDetailsModel) {
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDetailsModel.getFirstName()+" "+loginDetailsModel.getLastName(),
																				   loginDetailsModel.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String authenticationToken =  tokenProvider.generateToken(authentication);
		
		AuthenticationRest response = new AuthenticationRest();
		
		System.out.println("Authentication Token -> "+authenticationToken);
		
		response.setAuthenticationToken(authenticationToken);
		response.setUserName(loginDetailsModel.getFirstName()+" "+loginDetailsModel.getLastName());
		
		response.setUserId(userRepository.findUserIdByFirstNameAndLastName(loginDetailsModel.getFirstName(),loginDetailsModel.getLastName()));
		
		return response;
		
	}

	@Override
	public void savePostAndComment(UserEntity user,CommentEntity savedComment) {
		
		
		if(user.getCommentDetails() == null) {
			List<CommentEntity> comments = new ArrayList<>();
			comments.add(savedComment);
			user.setCommentDetails(comments);
		}
		else {
			List<CommentEntity> comments = user.getCommentDetails();
			comments.add(savedComment);
			user.setCommentDetails(comments);
		}
		
		
		userRepository.save(user);
		
	}

	@Override
	public void savePostAndSubreddit(PostEntity savedPost, SubredditEntity savedSubreddit, UserEntity user) {
		
		if(user.getPostDetails() == null) {
			List<PostEntity> posts = new ArrayList<>();
			posts.add(savedPost);
			user.setPostDetails(posts);
		}
		else {
			List<PostEntity> posts = user.getPostDetails();
			posts.add(savedPost);
			user.setPostDetails(posts);
		}
		
		List<SubredditEntity> subreddits = user.getSubredditDetails();
		
		for(int i=0;i<subreddits.size();i++) {
			
			if(subreddits.get(i).getSubredditId().equals(savedSubreddit.getSubredditId())) {
				subreddits.set(i, savedSubreddit);
				break;
			}
			
		}
		
		user.setSubredditDetails(subreddits);
		
		userRepository.save(user);
	}

	@Override
	public void updateUserPost(PostEntity savedPost, UserEntity userDetails) {
	
		List<PostEntity> posts = userDetails.getPostDetails();
		
		for(int i=0;i<posts.size();i++) {
			if(posts.get(i).getPostId().equals(savedPost.getPostId())) {
				posts.set(i, savedPost);
				break;
			}
		}
		
		userDetails.setPostDetails(posts);
		
		userRepository.save(userDetails);
	}

	@Override
	public String getCurrentUser() {
		
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		
		return principal.getName();
		
	}

	@Override
	public UserEntity getUserFromUserName(String userName) {
		
		String[] str = utils.filterUserName(userName);
		
		System.out.println(userName);
		
		return userRepository.findUserByFirstNameAndLastName(str[0], str[1]);
		
	}

	@Override
	public void saveVoteToUser(VoteEntity savedVote, PostEntity post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserEntity saveVoteToUser(VoteEntity savedVote, UserEntity user) {
		
		if(user.getVoteDetails() == null) {
			List<VoteEntity> votes = new ArrayList<>();
			votes.add(savedVote);
			user.setVoteDetails(votes);
		}
		else {
			List<VoteEntity> votes = user.getVoteDetails();
			votes.add(savedVote);
			user.setVoteDetails(votes);
		}
		
		return userRepository.save(user);
		
	}

	@Override
	public UserEntity getUserFromUserId(String userId) {
		
		return userRepository.findUserByUserId(userId);
		
	}

	
}
