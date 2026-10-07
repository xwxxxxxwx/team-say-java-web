package com.xwx.xwx.service;

import com.xwx.xwx.mapper.ClickCountAnalysisMapper;
import com.xwx.xwx.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClickCountAnalysisService {

    @Autowired
    private ClickCountAnalysisMapper clickCountAnalysisMapper;

    // 根据 courseId 查询 menu 表并返回树形目录结构
    public List<Menu> getMenuByCourseId(String courseId) {
        List<Menu> allMenus = clickCountAnalysisMapper.selectMenuByCourseId(courseId);

        // 构建树形目录结构
        Map<Integer, Menu> menuMap = new HashMap<>();
        List<Menu> rootMenus = new ArrayList<>();

        // 将所有菜单项放入 map 中，key 为 node_id
        for (Menu menu : allMenus) {
            menuMap.put(menu.getNodeId(), menu);
        }

        // 构建树形结构
        for (Menu menu : allMenus) {
            Integer parentId = menu.getParentId();
            if (parentId == null) {
                // 没有父节点，说明是一级目录
                rootMenus.add(menu);
            } else {
                // 有父节点，将当前节点加入父节点的子节点列表中
                Menu parentMenu = menuMap.get(parentId);
                if (parentMenu != null) {
                    parentMenu.addChild(menu);
                }
            }
        }

        return rootMenus; // 返回根节点列表
    }
}
