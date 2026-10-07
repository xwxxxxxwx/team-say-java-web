package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.service.CourseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/createModel")
    public String showCreateCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "createModel";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute Course course, HttpSession session, Model model) {
        try {
            Long teacherId = (Long) session.getAttribute("userId");
            if (teacherId == null) {
                model.addAttribute("errorMessage", "未登录或用户信息丢失，请重新登录");
                return "createModel";
            }

            course.setTeacherId(teacherId);
            courseService.createCourse(course);

            return "redirect:/api/users/dashboardT?creationSuccess=true";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "课程创建失败: " + e.getMessage());
            return "createModel";
        }
    }

    @GetMapping("/teacher-courses")
    public String getTeacherCourses(HttpSession session, Model model) {
        Long teacherId = (Long) session.getAttribute("userId");
        if (teacherId == null) {
            return "redirect:/api/users/login";
        }
        List<Course> courses = courseService.getCoursesByTeacherId(teacherId);
        model.addAttribute("courses", courses);
        return "dashboardT";
    }
}