package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private long commentId;


    @Column(name = "comment",updatable = true , unique = false)
    @NotBlank
    @Size(min=5 , max = 100 , message = "Length should be in between 5 to 30")
    private String comment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserCommentData userCommentData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private BlogPostData blogPostData;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_reply_id" , referencedColumnName = "comment_id")
    private List<Reply> reply;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserCommentData getUserCommentData() {
        return userCommentData;
    }

    public void setUserCommentData(UserCommentData userCommentData) {
        this.userCommentData = userCommentData;
    }

    public BlogPostData getBlogPostData() {
        return blogPostData;
    }

    public void setBlogPostData(BlogPostData blogPostData) {
        this.blogPostData = blogPostData;
    }

    public List<Reply> getReply() {
        return reply;
    }

    public void setReply(List<Reply> reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", userCommentData=" + userCommentData +
                ", blogPostData=" + blogPostData +
                ", reply=" + reply +
                '}';
    }
}
