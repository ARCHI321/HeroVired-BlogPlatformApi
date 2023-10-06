package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id" , unique = true)
    private long userId;

    @Column(name="admin_id")
    @NotNull
    private boolean isAdmin;


    @Column(name = "user_name",updatable = false , unique = true)
    @NotBlank
    @Size(min=5 , max = 30 , message = "Length should be in between 5 to 30")
    private String userName;

    @Column(name = "user_password",updatable = true , unique = false)
    @NotBlank
    @Size(min=5 , max = 30 , message = "Length should be in between 5 to 30")
    private String userPassword;


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", isAdmin=" + isAdmin +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}