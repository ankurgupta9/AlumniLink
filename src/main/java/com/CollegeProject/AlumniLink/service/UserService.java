package com.CollegeProject.AlumniLink.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CollegeProject.AlumniLink.model.Comment;
import com.CollegeProject.AlumniLink.model.Posts;
import com.CollegeProject.AlumniLink.model.Users;
import com.CollegeProject.AlumniLink.repository.PostRepository;
import com.CollegeProject.AlumniLink.repository.UserRepository;
import com.CollegeProject.AlumniLink.repository.commentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PostRepository postrepo;
	
	@Autowired
	private commentRepository commentrepo;
	
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

	public Posts getPostById(Integer id){
		Posts post = postrepo.findById(id).orElse(null);
		return post;
	}

	public void updateUser(Users updatedUser) {
        // fetch existing user from DB
        Users existingUserOpt = repo.findById(updatedUser.getId()).orElse(null);

        if (existingUserOpt!=null) {
            Users existingUser = existingUserOpt;

            // Only update the profile fields
            existingUser.setAbout(updatedUser.getAbout());
            existingUser.setCurrentCompany(updatedUser.getCurrentCompany());
            existingUser.setCompanyProfile(updatedUser.getCompanyProfile());
            existingUser.setLinkedinUrl(updatedUser.getLinkedinUrl());

            repo.save(existingUser);
        }
	}

	public boolean savePost(Posts post, Integer userId) {
		try {
			Users user = repo.findById(userId).orElse(null);
			post.setUser(user);
			postrepo.save(post);
			log.info("User created a new post");
			return true;
		}
		catch(Exception e){
			log.warn("Exception occured");
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
	public List<Posts> getPostsByTag(String tag) {
		return postrepo.findByTag(tag);
	}

    public List<Users> getAllUsers() {
        return repo.findAll();
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

	public boolean deletePost(Integer id){
		Posts post = postrepo.findById(id).orElse(null);
		if(post!=null){
			postrepo.deleteById(id);
			return true;
		}else{
			return false;
		}
	}

	public void save(Comment comment) {
        commentrepo.save(comment);
    }

    public List<Comment> getCommentsByPost(Posts post) {
        return commentrepo.findByPost(post);
    }
}
