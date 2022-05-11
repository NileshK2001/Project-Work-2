package com.blogapp.app.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.app.config.AppConstants;
import com.blogapp.app.dto.PostDTO;
import com.blogapp.app.dto.PostResponse;
import com.blogapp.app.entities.Category;
import com.blogapp.app.entities.Post;
import com.blogapp.app.entities.User;
import com.blogapp.app.exceptions.ResourceNotFoundException;
import com.blogapp.app.repositories.CategoryRepo;
import com.blogapp.app.repositories.PostRepo;
import com.blogapp.app.repositories.UserRepository;
import com.blogapp.app.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImgName("default.png");
		post.setAddDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);

		return this.modelMapper.map(newPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImgName(postDto.getImgName());

		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public PostDTO getPostById(Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = null;
		if(sortDir.equals(AppConstants.SORT_DIR)) {
			
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		// Getting Data of a particular Page
		Page<Post> pagePosts = this.postRepo.findAll(p);

		List<Post> allPosts = pagePosts.getContent();

		List<PostDTO> allPostsDTO = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(allPostsDTO);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());

		return postResponse;
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = null;
		if(sortDir.equals(AppConstants.SORT_DIR)) {
			
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category ID", categoryId));
		
		Page<Post> pagePosts = this.postRepo.getByCategory(category, p);
		
		List<Post> posts = pagePosts.getContent();
		
		List<PostDTO> postsDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postsDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());

		return postResponse;
	}

	@Override
	public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = null;
		if(sortDir.equals(AppConstants.SORT_DIR)) {
			
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User ID", userId));
		
		Page<Post> pagePosts = this.postRepo.getByUser(user,p);
		
		List<Post> posts = pagePosts.getContent();
		
		List<PostDTO> postsDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(postsDto);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());

		return postResponse;
		
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		
		List<Post> searched = this.postRepo.searchByTitle(keyword);
		
		List<PostDTO> searchedDto = searched.stream().map((post)-> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return searchedDto;
	}

}
