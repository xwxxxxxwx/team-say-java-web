package com.xwx.xwx.service;

import com.xwx.xwx.pojo.Menu;

public interface MenuService {
    void createMenu(Menu menu);
    Integer findNodeIdByMenuNode(String courseId, String menuNode);
    void deleteByCourseId(String courseId);
    void incrementClickCount(int nodeId, String courseId);
    String getMenuNodeByQuizNumber(String quizNumber);
}