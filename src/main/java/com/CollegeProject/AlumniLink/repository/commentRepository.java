package com.CollegeProject.AlumniLink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CollegeProject.AlumniLink.model.Comment;
import com.CollegeProject.AlumniLink.model.Posts;

public interface commentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Posts post); // get all comments for a post
}

