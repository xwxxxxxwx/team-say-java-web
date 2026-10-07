package com.xwx.xwx.pojo;

import java.util.List;

public class ClickCountAnalysisResult {
    private int nodeId;
    private String courseId;
    private String menuNode;
    private Integer parentId;
    private String category;
    private String description;
    private String quizNumber;
    private int clickCount;
    private List<ClickCountAnalysisResult> children;

    // Getters and setters

    // toString method


    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMenuNode() {
        return menuNode;
    }

    public void setMenuNode(String menuNode) {
        this.menuNode = menuNode;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(String quizNumber) {
        this.quizNumber = quizNumber;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public List<ClickCountAnalysisResult> getChildren() {
        return children;
    }

    public void setChildren(List<ClickCountAnalysisResult> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ClickCountAnalysisResult{" +
                "nodeId=" + nodeId +
                ", courseId='" + courseId + '\'' +
                ", menuNode='" + menuNode + '\'' +
                ", parentId=" + parentId +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", quizNumber='" + quizNumber + '\'' +
                ", clickCount=" + clickCount +
                ", children=" + children +
                '}';
    }
}