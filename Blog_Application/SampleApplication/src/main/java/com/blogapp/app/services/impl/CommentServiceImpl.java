package com.blogapp.app.services.impl;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.app.dto.CommentDTO;
import com.blogapp.app.entities.Comment;
import com.blogapp.app.entities.Post;
import com.blogapp.app.entities.User;
import com.blogapp.app.exceptions.ResourceNotFoundException;
import com.blogapp.app.repositories.CommentRepo;
import com.blogapp.app.repositories.PostRepo;
import com.blogapp.app.repositories.UserRepository;
import com.blogapp.app.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDTO createComment(CommentDTO commentDto, Integer postId, Integer userId) {
		
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post", "Post ID", postId));
		
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResourceNotFoundException("User", "User ID", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setAddDate(new Date());
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment ID", commentId));

		this.commentRepo.delete(comment);
	}

	@Override
	public CommentDTO updateComment(CommentDTO commentDto, Integer commentId) {

		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment ID", commentId));

		comment.setContent(commentDto.getContent());

		Comment updatedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(updatedComment, CommentDTO.class);
	}

}
