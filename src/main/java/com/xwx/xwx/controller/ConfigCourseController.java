package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.service.ConfigCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class ConfigCourseController {

    @Autowired
    private ConfigCourseService configCourseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseConfig(@PathVariable String courseId) {
        Course course = configCourseService.getCourseById(courseId);
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<String> updateCourseConfig(@PathVariable String courseId, @RequestBody Course course) {
        course.setCourseId(courseId);
        try {
            configCourseService.updateCourse(course);
            return ResponseEntity.ok("Course updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update course: " + e.getMessage());
        }
    }
}