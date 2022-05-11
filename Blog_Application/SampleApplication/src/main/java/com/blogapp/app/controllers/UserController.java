package com.blogapp.app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.app.dto.ApiResponse;
import com.blogapp.app.dto.UserDTO;
import com.blogapp.app.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//CREATE USER
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto)
	{
		UserDTO createuserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createuserDto, HttpStatus.CREATED);
	}
	
	//DELETE USER
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userID)
	{
		userService.deleteUser(userID);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	//GET USERS
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.ok(userService.getAllUsers());
	}
	
	//GET USER FOR AN ID
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Integer userID){
		return ResponseEntity.ok(userService.getUserById(userID));
	}
	
	//UPDATE USER
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userdto, @PathVariable("userId") Integer userId)
	{
		UserDTO updateduser = userService.updateUser(userdto, userId);	
		return ResponseEntity.ok(updateduser);
	}
}
