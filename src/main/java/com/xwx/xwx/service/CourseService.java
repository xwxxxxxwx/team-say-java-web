package com.xwx.xwx.service;

import com.xwx.xwx.mapper.CourseMapper;
import com.xwx.xwx.pojo.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public void createCourse(Course course) {
        String courseId = generateCourseId(course.getCourseCategory());
        course.setCourseId(courseId);
        courseMapper.insertCourse(course);
    }

    private String generateCourseId(String category) {
        String prefix;
        switch (category.toLowerCase()) {
            case "science":
                prefix = "A";
                break;
            case "art":
                prefix = "B";
                break;
            case "engine":
                prefix = "C";
                break;
            case "math":
                prefix = "D";
                break;
            default:
                throw new IllegalArgumentException("Invalid course category");
        }

        String lastCourseId = courseMapper.getLastCourseIdByCategory(category);
        if (lastCourseId == null) {
            return prefix + "01";
        }

        int lastNumber = Integer.parseInt(lastCourseId.substring(1));
        return prefix + String.format("%02d", lastNumber + 1);
    }

    public List<Course> getCoursesByTeacherId(Long teacherId) {
        return courseMapper.getCoursesByTeacherId(teacherId);
    }

    public Course findById(String courseId) {
        return courseMapper.findById(courseId);
    }

    public void updateCourse(Course course) {
        courseMapper.updateCourse(course);
    }

    // 新增方法：根据课程ID获取课程信息
    public Course getCourseById(String courseId) {
        return courseMapper.getCourseById(courseId);
    }
}