package com.blogapp.app.services;

import java.util.List;

import com.blogapp.app.dto.PostDTO;
import com.blogapp.app.dto.PostResponse;

public interface PostService {

	// create
	public PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId);

	// delete
	public void deletePost(Integer postId);
	
	// update
	public PostDTO updatePost(PostDTO postDto, Integer postId);

	// get
	public PostDTO getPostById(Integer postId);

	// get all posts
	public PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get all posts by category
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get all posts by user
	public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	//search post
	public List<PostDTO> searchPosts(String keyword);
}
