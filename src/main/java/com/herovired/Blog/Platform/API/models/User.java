package com.herovired.Blog.Platform.API.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Set;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id" , unique = true)
    private long userId;

    @Column(name="is_admin")
    @NotNull
    private boolean isAdmin;


    @Column(name = "user_name",updatable = false , unique = true)
    @NotBlank
    @Size(min=5 , max = 30 , message = "Length should be in between 5 to 30")
    private String userName;

    @Column(name = "user_password",updatable = true , unique = false)
    @NotBlank
    @Size(min=5 , max = 300 , message = "Length should be in between 5 to 30")
    private String userPassword;

    @Column(name = "is_blocked" , updatable = true)
    private boolean isBlocked = false;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Authority> authorities;

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

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", isAdmin=" + isAdmin +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", isBlocked=" + isBlocked +
                ", authorities=" + authorities +
                '}';
    }
}