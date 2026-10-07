package com.xwx.xwx.service;

import com.xwx.xwx.pojo.Class;
import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.mapper.StudentCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentCourseService {

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    public List<Course> getCoursesByUserId(String userId) {
        return studentCourseMapper.getCoursesByUserId(userId);
    }

    public boolean verifyCourse(String courseId, String coursePin) {
        Course course = studentCourseMapper.getCourseById(courseId);
        return course != null && coursePin.equals(course.getCoursePin());
    }

    public void enrollInCourse(String userId, String courseId) {
        Class enrollment = new Class();
        enrollment.setUserId(userId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrolledAt(LocalDateTime.now());
        studentCourseMapper.insertEnrollment(enrollment);
    }
}