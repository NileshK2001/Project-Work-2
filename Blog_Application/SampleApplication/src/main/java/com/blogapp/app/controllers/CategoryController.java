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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.app.dto.ApiResponse;
import com.blogapp.app.dto.CategoryDTO;
import com.blogapp.app.repositories.CategoryRepo;
import com.blogapp.app.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto)
	{
		CategoryDTO createdCat = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDTO>(createdCat, HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable("categoryId") Integer categoryId)
	{
		CategoryDTO updatedCat = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDTO>(updatedCat, HttpStatus.OK);
	}

	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId)
	{
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully!!", true), HttpStatus.OK);
	}
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable("categoryId") Integer categoryId)
	{
		CategoryDTO cat = this.categoryService.getCategory(categoryId);
		return new ResponseEntity<CategoryDTO>(cat, HttpStatus.OK);
	}

	//get-all
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getCategories()
	{
		List<CategoryDTO> categories = this.categoryService.getCategories();
		return new ResponseEntity<List<CategoryDTO>>(categories, HttpStatus.OK);
	}

}
