package com.blogapp.app.repositories;

import com.blogapp.app.entities.Category;
import com.blogapp.app.entities.Post;
import com.blogapp.app.entities.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostRepo extends JpaRepository<Post, Integer> {

	Page<Post> getByUser(User user, Pageable p);
	Page<Post> getByCategory(Category category, Pageable p);
	
	@Query("select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String keyword);
}
