package com.CollegeProject.AlumniLink.Entry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Integer>{
	
	Users findByEmail (String email);
}
