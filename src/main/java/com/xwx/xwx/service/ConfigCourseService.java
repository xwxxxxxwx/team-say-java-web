package com.xwx.xwx.service;

import com.xwx.xwx.mapper.ConfigCourseMapper;
import com.xwx.xwx.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfigCourseService {

    @Autowired
    private ConfigCourseMapper configCourseMapper;

    public Course getCourseById(String courseId) {
        return configCourseMapper.selectCourseById(courseId);
    }

    @Transactional
    public void updateCourse(Course course) {
        int result = configCourseMapper.updateCourse(course);
        if (result == 0) {
            throw new RuntimeException("Course not found or no changes made");
        }
    }
}