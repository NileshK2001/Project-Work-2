package com.blogapp.app.repositories;

import com.blogapp.app.entities.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
