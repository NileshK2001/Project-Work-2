package com.blogapp.app.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {
	
	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be of minimum 4 characters")
	private String name;
	
	@Email(message = "Your entered email is not valid")
	private String email;
	
	@NotEmpty
	private String password;
	
	@NotEmpty
	@Size(min = 3, max = 12, message="Password must be min of 3 chars and max of 10 chars!!")
	private String about;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

}
