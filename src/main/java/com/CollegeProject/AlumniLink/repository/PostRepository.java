package com.CollegeProject.AlumniLink.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.CollegeProject.AlumniLink.model.Posts;

import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer>{

	List<Posts> findByUserId(Integer userId);
	List<Posts> findByUser_collegeOrderByCreatedAtDesc(String college);
	List<Posts> findByTag(String tag);

}

