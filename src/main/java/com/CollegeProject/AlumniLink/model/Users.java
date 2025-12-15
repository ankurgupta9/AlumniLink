package com.CollegeProject.AlumniLink.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String email;
	private String password;
	private String college;
	private String enrollment;
	private String Course;
	private String currentCompany;
	private String CompanyProfile;
    private String about;
    private String linkedinUrl;
	@Lob
@Column(name = "profile_image", columnDefinition = "LONGBLOB")
private byte[] profileImage;

	// private String UserType;

	
//	public Users(Integer id, String name, String email, String password, String college) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.email = email;
//		this.password = password;
//		this.college = college;
//	}

public byte[] getProfileImage() {
    return profileImage;
}

public void setProfileImage(byte[] profileImage) {
    this.profileImage = profileImage;
}



    public String getCurrentCompany() {
		return currentCompany;
	}

	public void setCurrentCompany(String currentCompany) {
		this.currentCompany = currentCompany;
	}

	public String getCompanyProfile() {
		return CompanyProfile;
	}

	public void setCompanyProfile(String companyProfile) {
		CompanyProfile = companyProfile;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getCourse() {
		return Course;
	}

	public void setCourse(String course) {
		Course = course;
	}

	public String getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(String enrollment) {
		this.enrollment = enrollment;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posts> posts; // A user can have multiple posts

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", college="
				+ college + ", enrollment=" + enrollment + ", Course=" + Course + ", currentCompany=" + currentCompany
				+ ", CompanyProfile=" + CompanyProfile + ", about=" + about + ", linkedinUrl=" + linkedinUrl + "]";
	}

	

	

}
