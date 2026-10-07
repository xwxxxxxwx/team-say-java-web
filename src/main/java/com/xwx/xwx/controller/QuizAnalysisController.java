package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.ScoreDistribution;
import com.xwx.xwx.service.QuizAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quiz-analysis")
public class QuizAnalysisController {

    @Autowired
    private QuizAnalysisService quizAnalysisService;

    // 根据 courseId 获取 Quiz 列表
    @GetMapping("/getQuizNumbers")
    public List<String> getQuizNumbers(@RequestParam String courseId) {
        return quizAnalysisService.getQuizNumbersByCourseId(courseId);
    }

    // 根据 quizNumber 获取问题列表
    @GetMapping("/getQuestionsByQuiz")
    public List<String> getQuestionsByQuiz(@RequestParam String quizNumber) {
        return quizAnalysisService.getQuestionsByQuizNumber(quizNumber);
    }

    // 根据 quizNumber 和 questionOrder 获取统计数据
    @GetMapping("/getQuizStatistics")
    public Map<String, Object> getQuizStatistics(@RequestParam String quizNumber, @RequestParam String questionOrder) {
        return quizAnalysisService.getQuizStatistics(quizNumber, questionOrder);
    }

    @GetMapping("/getPassRate")
    public Map<String, Double> getPassRate(@RequestParam String quizNumber, @RequestParam String questionOrder) {
        double passRate = quizAnalysisService.getPassRate(quizNumber, questionOrder);
        Map<String, Double> response = new HashMap<>();
        response.put("pass_rate", passRate);
        return response;
    }
    @GetMapping("/getScoreDistribution")
    public ScoreDistribution getScoreDistribution(@RequestParam String quizNumber, @RequestParam String questionOrder) {
        return quizAnalysisService.calculateScoreDistribution(quizNumber, questionOrder);
    }
}
