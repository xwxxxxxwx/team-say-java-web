package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class PageController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/search")
    public String showSearchPage() {
        return "search"; // 确保 search3.html 位于 templates 目录中
    }

    @GetMapping("/data-analysis")
    public String showDataAnalysisPage(@RequestParam String courseId, Model model) {
        // 从数据库中获取 Course 信息
        Course course = courseService.getCourseById(courseId);

        // 检查课程是否存在
        if (course != null) {
            model.addAttribute("course", course); // 直接传递整个 Course 对象
        }
        return "data-analysis"; // 确保 data-analysis.html 位于 templates 目录中
    }

}

// 数据模型（可以移到单独的文件中）
class CourseAnalytics {
    private String courseId;
    private String courseName;
    private int totalStudents;
    private double averageScore;
    private int completionRate;
    private int[] gradeDistribution;

    // 构造函数
    public CourseAnalytics(String courseId, String courseName, int totalStudents,
                           double averageScore, int completionRate, int[] gradeDistribution) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.totalStudents = totalStudents;
        this.averageScore = averageScore;
        this.completionRate = completionRate;
        this.gradeDistribution = gradeDistribution;
    }

    // Getter 方法
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getTotalStudents() { return totalStudents; }
    public double getAverageScore() { return averageScore; }
    public int getCompletionRate() { return completionRate; }
    public int[] getGradeDistribution() { return gradeDistribution; }
}
