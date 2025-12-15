package com.CollegeProject.AlumniLink.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 🔗 Link to the Post
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    // 🔗 Link to the User who commented
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private String content; // the comment text

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    

    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
        this.id = id;
    }



    public Posts getPost() {
        return post;
    }



    public void setPost(Posts post) {
        this.post = post;
    }



    public Users getUser() {
        return user;
    }



    public void setUser(Users user) {
        this.user = user;
    }



    public String getContent() {
        return content;
    }



    public void setContent(String content) {
        this.content = content;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }



    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



    @Override
    public String toString() {
        return "Comment [id=" + id + ", post=" + post + ", user=" + user + ", content=" + content + ", createdAt="
                + createdAt + "]";
    }

    
}

