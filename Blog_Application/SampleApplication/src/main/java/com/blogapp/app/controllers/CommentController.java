package com.blogapp.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.app.dto.ApiResponse;
import com.blogapp.app.dto.CommentDTO;
import com.blogapp.app.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	//create
	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDto, @PathVariable Integer postId, @PathVariable Integer userId){
		
		CommentDTO createdComment = this.commentService.createComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDTO>(createdComment,HttpStatus.CREATED);
	}
	
	//delete
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted Successfully!!!", true), HttpStatus.OK);
	}
	
	//update
	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@RequestBody CommentDTO commentDto, @PathVariable Integer commentId){
		
		CommentDTO updatedComment = this.commentService.updateComment(commentDto, commentId);
		
		return ResponseEntity.ok(updatedComment);
	}
}
