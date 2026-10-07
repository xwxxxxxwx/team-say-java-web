package com.xwx.xwx.pojo;

import java.time.LocalDateTime;

public class Class {
    private String userId;
    private String courseId;
    private LocalDateTime enrolledAt;

    public Class() {
    }

    public Class(String userId, String courseId, LocalDateTime enrolledAt) {
        this.userId = userId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    @Override
    public String toString() {
        return "Class{" +
                "userId='" + userId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", enrolledAt=" + enrolledAt +
                '}';
    }
}
