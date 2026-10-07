package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.Feedback;
import com.xwx.xwx.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/feedback")
    public ResponseEntity<List<Map<String, Object>>> getAllFeedback(HttpSession session) {
        // 从 session 中获取 userId 并进行安全的类型转换
        Object userIdObj = session.getAttribute("userId");
        String teacherId;
        if (userIdObj instanceof Long) {
            teacherId = String.valueOf(userIdObj);
        } else if (userIdObj instanceof String) {
            teacherId = (String) userIdObj;
        } else {
            // 处理 userId 不存在或类型不正确的情况
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(reportService.getAllFeedback(teacherId));
    }

    @GetMapping("/feedback/{courseId}")
    public ResponseEntity<List<Map<String, Object>>> getFeedbackByCourse(@PathVariable String courseId, HttpSession session) {
        // 从 session 中获取 userId 并进行安全的类型转换
        Object userIdObj = session.getAttribute("userId");
        String teacherId;
        if (userIdObj instanceof Long) {
            teacherId = String.valueOf(userIdObj);
        } else if (userIdObj instanceof String) {
            teacherId = (String) userIdObj;
        } else {
            // 处理 userId 不存在或类型不正确的情况
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(reportService.getFeedbackByCourse(teacherId, courseId));
    }



//    @GetMapping("/feedback")
//    public ResponseEntity<List<Feedback>> getAllFeedback() {
//        return ResponseEntity.ok(reportService.getAllFeedback());
//    }

//    @GetMapping("/feedback/{courseId}")
//    public ResponseEntity<List<Feedback>> getFeedbackByCourse(@PathVariable String courseId) {
//        return ResponseEntity.ok(reportService.getFeedbackByCourse(courseId));
//    }

    @PutMapping("/feedback/{id}/read")
    public ResponseEntity<Void> markFeedbackAsRead(@PathVariable int id) {
        reportService.markFeedbackAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/feedback/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable int id) {
        reportService.deleteFeedback(id);
        return ResponseEntity.ok().build();
    }
}