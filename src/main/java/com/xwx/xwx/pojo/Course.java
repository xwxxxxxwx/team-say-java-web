package com.xwx.xwx.pojo;

import java.sql.Timestamp;

public class Course {

    private String courseId;
    private Long teacherId;
    private String courseName;
    private String courseCategory;
    private String courseDescription;
    private String coursePin;
    private String knowledgeBaseLink;
    private String aiApi;
    private String digitalHumanApi;
    private Timestamp createdAt;
    private boolean isReal;

    public Course() {
        // 这是无参构造
    }

    public Course(String courseId, Long teacherId, String courseName, String courseCategory, String courseDescription, String coursePin, String knowledgeBaseLink, String aiApi, String digitalHumanApi, Timestamp createdAt, boolean isReal) {
        this.courseId = courseId;
        this.teacherId = teacherId;
        this.courseName = courseName;
        this.courseCategory = courseCategory;
        this.courseDescription = courseDescription;
        this.coursePin = coursePin;
        this.knowledgeBaseLink = knowledgeBaseLink;
        this.aiApi = aiApi;
        this.digitalHumanApi = digitalHumanApi;
        this.createdAt = createdAt;
        this.isReal = isReal;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCoursePin() {
        return coursePin;
    }

    public void setCoursePin(String coursePin) {
        this.coursePin = coursePin;
    }

    public String getKnowledgeBaseLink() {
        return knowledgeBaseLink;
    }

    public void setKnowledgeBaseLink(String knowledgeBaseLink) {
        this.knowledgeBaseLink = knowledgeBaseLink;
    }

    public String getAiApi() {
        return aiApi;
    }

    public void setAiApi(String aiApi) {
        this.aiApi = aiApi;
    }

    public String getDigitalHumanApi() {
        return digitalHumanApi;
    }

    public void setDigitalHumanApi(String digitalHumanApi) {
        this.digitalHumanApi = digitalHumanApi;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isReal() {
        return isReal;
    }

    public void setReal(boolean real) {
        isReal = real;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", teacherId=" + teacherId +
                ", courseName='" + courseName + '\'' +
                ", courseCategory='" + courseCategory + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", coursePin='" + coursePin + '\'' +
                ", knowledgeBaseLink='" + knowledgeBaseLink + '\'' +
                ", aiApi='" + aiApi + '\'' +
                ", digitalHumanApi='" + digitalHumanApi + '\'' +
                ", createdAt=" + createdAt +
                ", isReal=" + isReal +
                '}';
    }
}
