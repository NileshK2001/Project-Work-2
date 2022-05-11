package com.blogapp.app.services;

import com.blogapp.app.dto.CommentDTO;

public interface CommentService {

	// create comment
	CommentDTO createComment(CommentDTO commentDto, Integer postId, Integer userId);
	
	// delete comment
	void deleteComment(Integer commentId);
	
	//update comment
	CommentDTO updateComment(CommentDTO commentDto, Integer commentId);
}
