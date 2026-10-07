package com.xwx.xwx.pojo;

import java.time.LocalDateTime;

public class Feedback {
    private int id;
    private String courseId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private boolean isRead;
    private String courseCategory;

    public Feedback() {
    }

    public Feedback(int id, String courseId, int rating, String comment, LocalDateTime createdAt, boolean isRead, String courseCategory) {
        this.id = id;
        this.courseId = courseId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.courseCategory = courseCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", courseId='" + courseId + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", isRead=" + isRead +
                ", courseCategory='" + courseCategory + '\'' +
                '}';
    }
}
