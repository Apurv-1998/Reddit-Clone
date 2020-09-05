package com.fullstack.redditclone.application.service.impl;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.redditclone.application.entity.UserEntity;
import com.fullstack.redditclone.application.entity.VerificationTokenEntity;
import com.fullstack.redditclone.application.repository.VerificationTokenRepository;
import com.fullstack.redditclone.application.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	VerificationTokenRepository tokenRepository;

	@Override
	public String createVerificationToken() {
		
		String verificationToken = UUID.randomUUID().toString();
		
		return verificationToken;
		
	}

	@Override
	@Transactional
	public UserEntity getUserForToken(String tokenId) {
		
		VerificationTokenEntity tokenEntity = tokenRepository.findTokenByTokenId(tokenId);
		
		UserEntity user = tokenEntity.getUserDetails();
		
		tokenRepository.delete(tokenEntity);
		
		return user;
	}

}
