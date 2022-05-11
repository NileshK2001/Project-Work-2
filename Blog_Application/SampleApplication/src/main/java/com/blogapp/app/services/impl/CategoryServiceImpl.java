package com.blogapp.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blogapp.app.dto.CategoryDTO;
import com.blogapp.app.entities.Category;
import com.blogapp.app.exceptions.ResourceNotFoundException;
import com.blogapp.app.repositories.CategoryRepo;
import com.blogapp.app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {
		
		Category category = this.modelMapper.map(categoryDto,Category.class);
		Category addedcat = this.categoryRepo.save(category);
		return this.modelMapper.map(addedcat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category", "Category ID", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedcat, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category", "Category ID", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()->new ResourceNotFoundException("Category", "Category ID", categoryId));
		
		return this.modelMapper.map(cat, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCategories() {
		
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDTO> catDTO = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());
		return catDTO;
	}
	

}
