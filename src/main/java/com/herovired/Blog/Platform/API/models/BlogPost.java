package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "blog_post")
public class BlogPost{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id" , unique = true)
    private long postId;

    @Column(name = "post_title",updatable = true , unique = false)
    @NotBlank
    @Size(min=5 , max = 100 , message = "Length should be in between 5 to 30")
    private String postTitle;

    @Column(name = "post_content",updatable = true , unique = false)
    @NotBlank
    @Size(min=5 , max = 100 , message = "Length should be in between 5 to 30")
    private String postContent;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_tag_id" , referencedColumnName = "post_id")
    private List<AllTags> allTags;



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id" , updatable = false , unique = false)
    private UserData userData;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="dd/MM/yyyy")
    @Column(name = "date_added",updatable = false , unique = false)
    private Date dateAdded;


    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public List<AllTags> getAllTags() {
        return allTags;
    }

    public void setAllTags(List<AllTags> allTags) {
        this.allTags = allTags;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "BlogPost{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", allTags=" + allTags +
                ", userData=" + userData +
                ", dateAdded=" + dateAdded +
                '}';
    }
}
