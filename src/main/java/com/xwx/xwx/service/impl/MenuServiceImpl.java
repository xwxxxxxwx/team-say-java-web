package com.xwx.xwx.service.impl;

import com.xwx.xwx.mapper.MenuMapper;
import com.xwx.xwx.pojo.Menu;
import com.xwx.xwx.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    @Transactional
    public void createMenu(Menu menu) {
        menuMapper.insertMenu(menu);
    }

    @Override
    public Integer findNodeIdByMenuNode(String courseId, String menuNode) {
        return menuMapper.findNodeIdByMenuNode(courseId, menuNode);
    }

    @Override
    public void deleteByCourseId(String courseId) {
        menuMapper.deleteByCourseId(courseId);
    }

    @Override
    @Transactional
    public void incrementClickCount(int nodeId, String courseId) {
        Menu menu = menuMapper.findByNodeIdAndCourseId(nodeId, courseId);
        if (menu == null) {
            throw new RuntimeException("Menu item not found");
        }
        menuMapper.incrementClickCount(nodeId, courseId);
    }

    @Override
    public String getMenuNodeByQuizNumber(String quizNumber) {
        // 实现根据 quizNumber 查找 menuNode 的逻辑
        return menuMapper.findMenuNodeByQuizNumber(quizNumber);
    }
}