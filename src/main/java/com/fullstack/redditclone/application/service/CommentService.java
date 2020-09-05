package com.fullstack.redditclone.application.service;

import com.fullstack.redditclone.application.dto.CreateCommentDto;
import com.fullstack.redditclone.application.entity.CommentEntity;

public interface CommentService {

	CommentEntity createComment(String postId, String postId2, CreateCommentDto commentDto);

}
