package com.blogapp.app.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blogapp.app.dto.CommentDTO;

public class PostDTO {

	private Integer postId;

	@NotEmpty
	@Size(min = 8, message = "Title must be of minimum 8 characters")
	private String title;

	@NotEmpty
	@Size(min = 12, message = "Content must be of minimum 12 characters")
	private String content;

	@NotEmpty
	private String imgName;

	@NotEmpty
	private Date addDate;

	@NotEmpty
	private UserDTO user;

	@NotEmpty
	private CategoryDTO category;

	@NotBlank
	private Set<CommentDTO> comments = new HashSet<CommentDTO>();

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public Set<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}

	public PostDTO(Integer postId,
			@NotEmpty @Size(min = 8, message = "Title must be of minimum 8 characters") String title,
			@NotEmpty @Size(min = 12, message = "Content must be of minimum 12 characters") String content,
			@NotEmpty String imgName, @NotEmpty Date addDate, @NotEmpty UserDTO user, @NotEmpty CategoryDTO category,
			Set<CommentDTO> comments) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.imgName = imgName;
		this.addDate = addDate;
		this.user = user;
		this.category = category;
		this.comments = comments;
	}

	public PostDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}