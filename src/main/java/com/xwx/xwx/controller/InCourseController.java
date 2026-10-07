package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.pojo.Menu;
import com.xwx.xwx.service.InCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InCourseController {

    @Autowired
    private InCourseService inCourseService;

    @GetMapping("/course-detail")
    public String getCourseDetail(@RequestParam String id, Model model) {
        Course course = inCourseService.getCourseById(id);
        List<Menu> courseMenu = inCourseService.getCourseMenuById(id);
        if (course != null) {
            model.addAttribute("course", course);
            model.addAttribute("courseMenu", courseMenu);

            // 添加这段代码来打印菜单信息
            for (Menu menu : courseMenu) {
                System.out.println("Controller - Menu node: " + menu.getMenuNode());
            }

            return "course-detail";
        } else {
            return "error";
        }
    }

    @GetMapping("/course-detailT")
    public String getCourseDetailT(@RequestParam String id, Model model) {
        Course course = inCourseService.getCourseById(id);
        List<Menu> courseMenu = inCourseService.getCourseMenuById(id);
        if (course != null) {
            model.addAttribute("course", course);
            model.addAttribute("courseMenu", courseMenu);

            // 添加这段代码来打印菜单信息
            for (Menu menu : courseMenu) {
                System.out.println("Controller - Menu node: " + menu.getMenuNode());
            }

            return "course-detailT";
        } else {
            return "error";
        }
    }
}