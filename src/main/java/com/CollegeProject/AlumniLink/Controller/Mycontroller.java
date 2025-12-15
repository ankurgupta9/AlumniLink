package com.CollegeProject.AlumniLink.Controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.CollegeProject.AlumniLink.model.Comment;
import com.CollegeProject.AlumniLink.model.Posts;
import com.CollegeProject.AlumniLink.model.Users;
import com.CollegeProject.AlumniLink.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

@Controller // MVC Controller or Web Controller
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

		boolean alreadyExists = service.RegisteredUsers(user.getEmail());

		if (alreadyExists) {
			model.addAttribute("errorMsg", "Email already registered");
			return "register";
		}

		String enrollment = user.getEnrollment();

		if (enrollment == null || enrollment.length() != 11) {
			model.addAttribute("errorMsg", "Invalid enrollment number format");
			return "register";
		}

		String collegeCode = enrollment.substring(3, 6); // index 3 to 5 (inclusive)
		String courseCode = enrollment.substring(6, 9); // index 6 to 8 (inclusive)

		if (!collegeCode.equals("111")) {
			model.addAttribute("errorMsg", "Only students from our college are allowed to register");
			return "register";
		}
		if (!courseCode.equals("044")) {
			user.setCourse("MCA");
		} else {
			user.setCourse("BCA");
		}

		user.setCollege("BCIIT");

		boolean status = service.registerUser(user);

		if (status) {
			model.addAttribute("successMsg", "Registered successfully");
		} else {
			model.addAttribute("errorMsg", "Error! Please try again.");
		}

		return "register";
	}

	@GetMapping("/profile")
	public String profilePage(Model model, HttpSession session) {

		Users details = (Users) session.getAttribute("loggedInUser");

		if (details == null) {
			return "redirect:/loginPage"; // Redirect if not logged in
		}

		// Extract batch year from enrollment
		String enrollment = details.getEnrollment();
		int batch = 2000 + Integer.parseInt(enrollment.substring(enrollment.length() - 2));
		int duration = details.getCourse().equalsIgnoreCase("BCA") ? 3 : 2;
		int currentYear = LocalDate.now().getYear();

		String userType = (batch + duration < currentYear) ? "Alumni" : "Student";
		model.addAttribute("userType", userType);

		// Add user details to model
		model.addAttribute("name", details.getName());
		model.addAttribute("email", details.getEmail());
		model.addAttribute("college", details.getCollege());
		model.addAttribute("enrollment", details.getEnrollment());
		model.addAttribute("course", details.getCourse());
		model.addAttribute("about", details.getAbout());
		model.addAttribute("currentcompany", details.getCurrentCompany());
		model.addAttribute("profile", details.getCompanyProfile());
		model.addAttribute("linkedin", details.getLinkedinUrl());

		List<Posts> userPosts = service.getPostsByUserId(details.getId());
		model.addAttribute("posts", userPosts);

		return "profile";
	}

	@GetMapping("/moredetails")
	public String showMoreDetailsForm(Model model, HttpSession session) {
		Users user = (Users) session.getAttribute("loggedInUser");
		model.addAttribute("user", user);
		return "userdetails";
	}

	@PostMapping("/moredetails")
	public String getdetails(@ModelAttribute("user") Users user, HttpSession session) {
		// user.setAbout("about");
		// user.setCurrentCompany("currentcompany");
		// user.setCompanyProfile("profile");
		// user.setLinkedinUrl("Linkedin");

		service.updateUser(user);
		return "redirect:/profile";
	}


	@GetMapping("/profileVisit/{id}")
	public String Visitorprofile(Model model, @PathVariable Integer id) {

		Users details = service.getUserbyid(id);

		if (details == null) {
			return "redirect:/loginPage"; // Redirect if not logged in
		}

		String enrollment = details.getEnrollment();
		int batch = 2000 + Integer.parseInt(enrollment.substring(enrollment.length() - 2));
		int duration = details.getCourse().equalsIgnoreCase("BCA") ? 3 : 2;
		int currentYear = LocalDate.now().getYear();

		String userType = (batch + duration < currentYear) ? "Alumni" : "Student";
		model.addAttribute("userType", userType);

		model.addAttribute("name", details.getName());
		model.addAttribute("email", details.getEmail());
		model.addAttribute("college", details.getCollege());
		model.addAttribute("enrollment", details.getEnrollment());
		model.addAttribute("course", details.getCourse());
		model.addAttribute("about", details.getAbout());
		model.addAttribute("currentcompany", details.getCurrentCompany());
		model.addAttribute("profile", details.getCompanyProfile());
		model.addAttribute("linkedin", details.getLinkedinUrl());

		List<Posts> userPosts = service.getPostsByUserId(details.getId());
		model.addAttribute("posts", userPosts);

		if(details.getId()!=999){
		return "profile";	
		}
		return "redirect:/home";

	}

	@GetMapping("/postPage")
	public String openCreatePost(HttpSession session, Model model) {
		Users user = (Users) session.getAttribute("loggedInUser");
		model.addAttribute("post", new Posts());
		model.addAttribute("username", user.getName());
		return "createPost";
	}

	@GetMapping("/post/{id}")
	public String viewPostWithComments(@PathVariable Integer id, Model model) {
		Posts post = service.getPostById(id);
		List<Comment> comments = service.getCommentsByPost(post);

		model.addAttribute("post", post);
		model.addAttribute("comments", comments);
		model.addAttribute("newComment", new Comment());

		return "postdetails"; // this is the name of your Thymeleaf page
	}

	@PostMapping("/comment")
	public String saveComment(@RequestParam Integer postId, @RequestParam String content, HttpSession session) {
		// Get logged-in user
		Users user = (Users) session.getAttribute("loggedInUser");

		// Get post object
		Posts post = service.getPostById(postId);

		// Create new comment
		Comment comment = new Comment();
		comment.setUser(user);
		comment.setPost(post);
		comment.setContent(content);

		// Save to DB
		service.save(comment);

		// Redirect back to post detail page
		return "redirect:/post/" + postId;
	}

	@GetMapping("/{id}")
	public String deletePost(@PathVariable Integer id) {
		service.deletePost(id);
		return "redirect:/profile";
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
		model.addAttribute("about", user.getAbout());
		model.addAttribute("college", user.getCollege());

		List<Posts> userPosts = service.getAllPosts(user.getId(), user.getCollege());
		model.addAttribute("posts", userPosts);

		return "home";
	}

	@GetMapping("/admin/dashboard")
	public String adminDashboard(HttpSession session, Model model) {
		// Boolean isAdmin = (Boolean) session.getAttribute("admin");
		Users user = (Users) session.getAttribute("loggedInUser");

		if (user == null || user.getId() != 999) {
			return "redirect:/loginPage";
		}

		// Show all users or posts from college
		List<Users> allUsers = service.getAllUsers(); // you'll need to create this method
		model.addAttribute("users", allUsers);

		return "adminDashboard";
	}

	@GetMapping("/admin/announcement")
	public String openAnnouncementPage(Model model) {
		model.addAttribute("post", new Posts());
		return "adminAnnouncement";
	}

	@PostMapping("/admin/postAnnouncement")
	public String saveAnnouncement(@ModelAttribute("post") Posts post, HttpSession session) {
		post.setCreatedAt(LocalDateTime.now());
		post.setTag("Announcement");
		post.setUser(null); // or some dummy admin user if required
		service.savePost(post, null); // Use your existing post saving method
		return "redirect:/admin/dashboard";
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

	@PostMapping("/search")
	public String SearchPage(@RequestParam String query, Model model) {

		List<Users> user = service.getUserbyname(query);
		model.addAttribute("user", user);
		return "search";
	}

	@GetMapping("/filter")
	public String filterPosts(@RequestParam(required = false) String tag, Model model, HttpSession session) {
		Users user = (Users) session.getAttribute("loggedInUser");
		List<Posts> posts;
		if (tag != null && !tag.isEmpty()) {
			posts = service.getPostsByTag(tag);
			model.addAttribute("selectedTag", tag);
		} else {
			posts = service.getAllPosts(user.getId(), user.getCollege()); // fallback to all
		}

		model.addAttribute("posts", posts);

		// Set user data (if needed)
		model.addAttribute("username", user.getName());
		model.addAttribute("college", user.getCollege());

		return "home";
	}

}

@RestController // REST API
class fetchUsers {

	@Autowired
	private UserService service;

	@GetMapping("/getallusers")
	public List<Users> getUsers() {
		return service.getUsersAll();
	}
}
