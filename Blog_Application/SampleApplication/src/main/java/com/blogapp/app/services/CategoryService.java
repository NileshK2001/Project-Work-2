package com.blogapp.app.services;

import java.util.List;

import com.blogapp.app.dto.CategoryDTO;

public interface CategoryService {

	//create
	public CategoryDTO createCategory(CategoryDTO categoryDto);
	
	//update
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId);
	
	//delete
	public void deleteCategory(Integer categoryId);
	
	//get
	public CategoryDTO getCategory(Integer categoryId);
	
	//get-all
	public List<CategoryDTO> getCategories();
}
