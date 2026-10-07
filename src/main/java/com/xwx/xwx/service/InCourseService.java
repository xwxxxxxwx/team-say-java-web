package com.xwx.xwx.service;

import com.xwx.xwx.mapper.QuizMapper;
import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.mapper.InCourseMapper;
import com.xwx.xwx.pojo.Menu;
import com.xwx.xwx.pojo.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InCourseService {

    @Autowired
    private InCourseMapper inCourseMapper;

    @Autowired
    private QuizMapper quizMapper;

    public Course getCourseById(String courseId) {
        return inCourseMapper.getCourseById(courseId);
    }

    public List<Menu> getCourseMenuById(String courseId) {
        List<Menu> flatMenu = inCourseMapper.getCourseMenuById(courseId);
        for (Menu menu : flatMenu) {
            System.out.println("Menu node: " + menu.getMenuNode() + ", Quiz Number: " + menu.getQuizNumber());
            List<Quiz> quizzes = quizMapper.getQuizzesByQuizNumber(menu.getQuizNumber());
            System.out.println("quizList: "+ quizzes);
            menu.setQuizList(quizzes); // 将查询结果设置到 Menu 的 quizList 属性中
        }
        return organizeMenu(flatMenu);
    }

    private List<Menu> organizeMenu(List<Menu> flatMenu) {
        Map<Integer, Menu> menuMap = new HashMap<>();
        List<Menu> rootMenu = new ArrayList<>();

        // First pass: map all menu items by their node_id
        for (Menu item : flatMenu) {
            menuMap.put(item.getNodeId(), item);
        }

        // Second pass: organize items into hierarchy
        for (Menu item : flatMenu) {
            if (item.getParentId() == null) {
                rootMenu.add(item);
            } else {
                Menu parent = menuMap.get(item.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(item);
                }
            }
        }

        return rootMenu;
    }
}