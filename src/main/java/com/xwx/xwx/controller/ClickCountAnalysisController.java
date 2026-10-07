package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Menu;
import com.xwx.xwx.service.ClickCountAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/click-count-analysis")
public class ClickCountAnalysisController {

    @Autowired
    private ClickCountAnalysisService clickCountAnalysisService;

    // 根据 courseId 获取目录的点击次数信息
    @GetMapping("/getCourseClickCount")
    public List<Menu> getCourseClickCount(@RequestParam("courseId") String courseId) {
        List<Menu> menuData = clickCountAnalysisService.getMenuByCourseId(courseId);
        // 调试打印
        System.out.println("Returning menu data: " + menuData);
        return menuData;
    }
}
