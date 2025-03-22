package com.CollegeProject.AlumniLink.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Posts, Integer>{

	List<Posts> findByUserId(Integer userId);
	List<Posts> findByUser_collegeOrderByCreatedAtDesc(String college);
}

