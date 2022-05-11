package com.blogapp.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.app.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
