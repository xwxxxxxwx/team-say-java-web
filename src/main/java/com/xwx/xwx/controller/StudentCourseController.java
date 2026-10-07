package com.xwx.xwx.controller;

import com.xwx.xwx.mapper.StudentCourseMapper;
import com.xwx.xwx.pojo.Class;
import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student/course")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addCourse(@RequestParam String courseId,
                                                         @RequestParam String coursePin,
                                                         @RequestParam String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean courseExists = studentCourseService.verifyCourse(courseId, coursePin);
            if (courseExists) {
                studentCourseService.enrollInCourse(userId, courseId);
                response.put("message", "Course addition success");
                response.put("redirectUrl", "/api/users/dashboardS");
            } else {
                response.put("message", "The course ID or PIN is incorrect");
            }
        } catch (Exception e) {
            response.put("message", "Failed to add a course, please try again later");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public List<Course> getCoursesByUserId(@RequestParam String userId) {
        return studentCourseService.getCoursesByUserId(userId);
    }
}