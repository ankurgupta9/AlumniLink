package com.CollegeProject.AlumniLink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CollegeProject.AlumniLink.model.Users;

public interface UserRepository extends JpaRepository<Users,Integer>{
	
	Users findByEmail (String email);
	List<Users> findByNameContainingIgnoreCase (String name);
}
