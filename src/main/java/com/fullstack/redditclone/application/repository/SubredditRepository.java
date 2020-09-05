package com.fullstack.redditclone.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.redditclone.application.entity.SubredditEntity;


@Repository
public interface SubredditRepository extends CrudRepository<SubredditEntity, Long> {

	SubredditEntity findSubredditBySubredditId(String subredditId);

	SubredditEntity findSubredditBySubredditName(String subredditName);
	

}
