package com.CollegeProject.AlumniLink.Entry;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PostRepository postrepo;
	
	public List<Users> getUsersAll(){
		return repo.findAll();
	}
	
	public boolean registerUser(Users user){
		
		try {
			repo.save(user);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	public boolean RegisteredUsers(String email) {
		Users user = repo.findByEmail(email);
		
		if(user != null) {
			return true;	
		}
		return false;
	}
	
	public Users profiledetails(String email) {
		Users user = repo.findByEmail(email);
		return user;
	}

	public List<Users> getUserbyname(String name){
		return repo.findByNameContainingIgnoreCase(name);
	}

	public Users getUserbyid(Integer id){
		Users user = repo.findById(id).orElse(null); 
		return user;
	}
	
	public boolean savePost(Posts post, Integer userId) {
		try {
			Users user = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			post.setUser(user);
			postrepo.save(post);
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	public List<Posts> getAllPosts(Integer userId, String college) {
	    try {
	        return postrepo.findByUser_collegeOrderByCreatedAtDesc(college);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}

	public String getPostsCreator(Integer Id){
		Posts post = postrepo.findById(Id).orElse(null);
		return post.getUser().getName();
    }

    // Get posts by user
    public List<Posts> getPostsByUserId(Integer userId) {
        return postrepo.findByUserId(userId);
    }
	
	public Users loginUser(String email, String password) {
		
		Users Validuser = repo.findByEmail(email);
		
		if(Validuser != null && Validuser.getPassword().equals(password)) {
			return Validuser;	
		}
		return null;
		  
	}
}
