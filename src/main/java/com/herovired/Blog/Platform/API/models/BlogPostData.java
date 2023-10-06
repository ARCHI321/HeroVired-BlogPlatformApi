package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class BlogPostData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" , unique = true , updatable = false)
    private long id;

    @Column(name = "post_id" , unique = false , updatable = false)
    private int postId;

    @Column(name = "post_title",updatable = true , unique = false)
    @Size(min=5 , max = 100 , message = "Length should be in between 5 to 30")
    private String postTitle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    @Override
    public String toString() {
        return "BlogPostData{" +
                "id=" + id +
                ", postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                '}';
    }
}
