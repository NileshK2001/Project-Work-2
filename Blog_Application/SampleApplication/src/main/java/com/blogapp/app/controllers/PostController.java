package com.blogapp.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.app.config.AppConstants;
import com.blogapp.app.dto.ApiResponse;
import com.blogapp.app.dto.PostDTO;
import com.blogapp.app.dto.PostResponse;
import com.blogapp.app.services.FileService;
import com.blogapp.app.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost( @RequestBody PostDTO postDto, @PathVariable("userId") Integer userId, @PathVariable("categoryId") Integer categoryId){
		
		PostDTO newPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDTO>(newPost, HttpStatus.CREATED);
	}
	
	//get by user
	//To give values to pgNumber and size /posts?pageNumber=<value>&pageSize=<value>
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable("userId") Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR , required = false) String sortDir)
	{
		PostResponse posts = this.postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(posts);
	}

	//get by category
	//To give values to pgNumber and size /posts?pageNumber=<value>&pageSize=<value>
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable("categoryId") Integer categoryId,			
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR , required = false) String sortDir)

	{
		PostResponse posts = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(posts);
	}

	//get all posts
	//To give values to pgNumber and size /posts?pageNumber=<value>&pageSize=<value>
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR , required = false) String sortDir)
	{
		PostResponse postResponse = this.postService.getPosts(pageNumber, pageSize, sortBy, sortDir); 
		return ResponseEntity.ok(postResponse);
		// Extra information about Post Response will be shown after the content
	}

	//get post by Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable("postId") Integer postId)
	{
		PostDTO post = this.postService.getPostById(postId);
		return ResponseEntity.ok(post);
	}

	//delete post by Id
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Successfully deleted!!", true), HttpStatus.OK);
	}

	//update post
	@PutMapping("/posts/{postId}")
	public  ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDto, @PathVariable Integer postId)
	{
		PostDTO updatedPost = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPost);
	}
	
	//search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable String keyword)
	{
		List<PostDTO> results = this.postService.searchPosts("%"+keyword+"%");
		return ResponseEntity.ok(results);
	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(
			@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException{
		
		PostDTO postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImgName(fileName);
		PostDTO updatedPost = this.postService.updatePost(postDto, postId); 
		
		return ResponseEntity.ok(updatedPost);
	}
	
	//method to serve files
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException
	{
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
