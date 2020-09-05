package com.fullstack.redditclone.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.redditclone.application.entity.PostEntity;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long> {

	PostEntity findPostByPostId(String postId);

}
