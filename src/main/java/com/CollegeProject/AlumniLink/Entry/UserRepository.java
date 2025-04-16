package com.CollegeProject.AlumniLink.Entry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer>{
	
	Users findByEmail (String email);
	List<Users> findByNameContainingIgnoreCase (String name);
}
