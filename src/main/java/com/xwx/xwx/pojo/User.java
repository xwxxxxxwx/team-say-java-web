package com.xwx.xwx.pojo;

import java.time.LocalDateTime;

public class User {
    private Long userId;
    private String username;
    private String major;
    private String email;
    private UserType userType;
    private String password;
    private LocalDateTime createdAt;

    // 默认构造函数
    public User() {}

    public User(Long userId, String username, String major, String email, UserType userType, String password, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.major = major;
        this.email = email;
        this.userType = userType;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum UserType {
        admin, teacher, student
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", major='" + major + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
