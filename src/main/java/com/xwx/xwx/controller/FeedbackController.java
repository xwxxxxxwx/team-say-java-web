package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.service.FeedbackService;
import com.xwx.xwx.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("/form")
    public String showFeedbackForm(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/api/users/login";
        }

        List<Course> courses = studentCourseService.getCoursesByUserId(userId.toString());
//        System.out.println("Courses retrieved: " + courses.size());
        for (Course course : courses) {
            System.out.println("Course: id=" + course.getCourseId() + ", name=" + course.getCourseName());
        }
        model.addAttribute("courses", courses);
        return "feedback";
    }

    @PostMapping("/submit")
    public String submitFeedback(
            @RequestParam String courseId,
            @RequestParam int rating,
            @RequestParam String comment,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            redirectAttributes.addFlashAttribute("error", "User not logged in");
            return "redirect:/api/users/login";
        }

        boolean success = feedbackService.submitFeedback(courseId, rating, comment);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Feedback submitted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to submit feedback");
        }

        return "redirect:/api/users/dashboardS";
    }
}