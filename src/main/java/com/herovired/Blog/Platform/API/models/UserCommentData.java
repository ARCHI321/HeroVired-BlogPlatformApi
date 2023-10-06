package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class UserCommentData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" , unique = true , updatable = false)
    private long id;

    @Column(name = "user_id" , unique = false , updatable = false)
    private int userId;



    @Column(name = "user_name",updatable = false)
    @Size(min=5 , max = 30 , message = "Length should be in between 5 to 30")
    private String userName;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "UserCommentData{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
