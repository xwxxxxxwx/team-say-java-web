package com.xwx.xwx.pojo;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private int nodeId;
    private String courseId;
    private String menuNode;
    private Integer parentId;
    private String category;
    private String description;
    private String quizNumber;
    private int clickCount;
    private List<Menu> children;

    private List<Quiz> quizList; // 新增字段，用来存储 quiz 列表
    public Menu() {
    }

    public Menu(int nodeId, String courseId, String menuNode, Integer parentId, String category, String description, String quizNumber, int clickCount, List<Menu> children) {
        this.nodeId = nodeId;
        this.courseId = courseId;
        this.menuNode = menuNode;
        this.parentId = parentId;
        this.category = category;
        this.description = description;
        this.quizNumber = quizNumber;
        this.clickCount = clickCount;
        this.children = children;
    }

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

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public void addChild(Menu child) {
        if (this.children == null) {
            this.children = new ArrayList<>(); // 如果 children 列表为 null，则初始化
        }
        this.children.add(child); // 将子节点添加到 children 列表中
    }

    @Override
    public String toString() {
        return "Menu{" +
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
