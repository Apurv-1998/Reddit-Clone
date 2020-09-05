package com.fullstack.redditclone.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fullstack.redditclone.application.entity.CommentEntity;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {

}
