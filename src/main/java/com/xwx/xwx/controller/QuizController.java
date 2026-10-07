package com.xwx.xwx.controller;

import com.xwx.xwx.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/get-user-id")
    public ResponseEntity<?> getUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        System.out.println(userId);
        if (userId != null) {
            return ResponseEntity.ok(Collections.singletonMap("userId", userId));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户未登录");
        }
    }

    @GetMapping("/start")
    public String startQuiz(@RequestParam String quizNumber, @RequestParam String courseId, Model model) {
        model.addAttribute("quizNumber", quizNumber);
        model.addAttribute("courseId", courseId);
        System.out.println(quizNumber);
        return "quiz";
    }

    @GetMapping("/get-max-order")
    public ResponseEntity<?> getMaxOrder(@RequestParam String quizNumber) {
        int maxOrder = quizService.findMaxQuestionOrder(quizNumber);
        System.out.println(quizNumber);
        return ResponseEntity.ok(Collections.singletonMap("maxOrder", maxOrder));
    }

    @GetMapping("/get-question")
    public ResponseEntity<?> getQuestion(@RequestParam String quizNumber, @RequestParam int questionOrder) {
        String question = quizService.findQuestionByOrder(quizNumber, questionOrder);
        return ResponseEntity.ok(Collections.singletonMap("question", question));
    }
}