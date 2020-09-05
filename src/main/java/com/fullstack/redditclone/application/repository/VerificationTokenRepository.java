package com.fullstack.redditclone.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.redditclone.application.entity.VerificationTokenEntity;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationTokenEntity, Long> {

	VerificationTokenEntity findTokenByTokenId(String tokenId);

}
