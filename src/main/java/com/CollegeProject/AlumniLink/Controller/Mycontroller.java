package com.CollegeProject.AlumniLink.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CollegeProject.AlumniLink.Entry.Posts;
import com.CollegeProject.AlumniLink.Entry.UserService;
import com.CollegeProject.AlumniLink.Entry.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
//import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/")
public class Mycontroller {

	@Autowired
	private UserService service;

	@GetMapping("/regPage")
	public String openRegPage(Model model) {
		model.addAttribute("user", new Users());
		return "register";
	}

	@PostMapping("/regForm")
	public String submitRegForm(@ModelAttribute("user") Users user, Model model) {

		boolean notvaliduser = service.RegisteredUsers(user.getEmail());
		boolean status = false;

		if (notvaliduser) {
			model.addAttribute("errorMsg", "Email already Registered");
			return "register";
		}

		status = service.registerUser(user);

		if (status) {
			model.addAttribute("successMsg", "Registered successfully");
		} else {
			model.addAttribute("errorMsg", "error try again");
		}

		return "register";
	}

	@GetMapping("/profile")
	public String profilePage(Model model, HttpSession session) {
		Users details = (Users) session.getAttribute("loggedInUser");

		if (details == null) {
			return "redirect:/loginPage"; // Redirect if not logged in
		}

		model.addAttribute("name", details.getName());
		model.addAttribute("email", details.getEmail());
		model.addAttribute("college", details.getCollege());
		
		List<Posts> userPosts = service.getPostsByUserId(details.getId());
	    model.addAttribute("posts", userPosts);
	    
		return "profile";
	}

	@GetMapping("/postPage")
	public String openCreatePost(HttpSession session, Model model) {
		Users user = (Users) session.getAttribute("loggedInUser");
		model.addAttribute("post", new Posts());
		model.addAttribute("username", user.getName());
		return "createPost";
	}

	@GetMapping("/loginPage")
	public String openLoginPage(Model model) {
		model.addAttribute("user", new Users());
		return "login";
	}

	@GetMapping("/")
	public String start() {
		return "redirect:/home";
	}

	@PostMapping("/home")
	public String submitLogin(@ModelAttribute("user") Users user, Model model, HttpSession session) {
		Users validUser = service.loginUser(user.getEmail(), user.getPassword());

		if (validUser != null) {
			session.setAttribute("loggedInUser", validUser); // Store user data in session
			return "redirect:/home"; // Redirect to GET /home
		} else {
			model.addAttribute("errorMsg", "Email or password is incorrect");
			return "login";
		}
	}

	@GetMapping("/home")
	public String homePage(Model model, HttpSession session) {
		Users user = (Users) session.getAttribute("loggedInUser");
		

		if (user == null) {
			return "redirect:/loginPage"; // Redirect to login if not logged in
		}

		model.addAttribute("username", user.getName());
		model.addAttribute("college", user.getCollege());
		
		List<Posts> userPosts = service.getAllPosts(user.getId(), user.getCollege());
	    model.addAttribute("posts", userPosts);
		
		return "home";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		return "redirect:/loginPage";
	}

	@PostMapping("/create")
	public String createPost(@ModelAttribute("post") Posts post, HttpSession session) {
		
		Users details = (Users) session.getAttribute("loggedInUser");

		boolean status = service.savePost(post, details.getId());
		if (status) {
			return "redirect:/home";
		}
		return "redirect:/postPage";
	}
	
}

@RestController
class fetchUsers{
	
	@Autowired
	private UserService service;
	
	@GetMapping("/getallusers")
	public List<Users> getUsers(){
		return service.getUsersAll();	
	}
}
