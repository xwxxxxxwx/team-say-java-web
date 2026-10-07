package com.xwx.xwx.controller;

import com.xwx.xwx.pojo.AnswerRequest;
import com.xwx.xwx.pojo.Quiz;
import com.xwx.xwx.service.AnswerService;
import com.xwx.xwx.service.QuizChatService;
import com.xwx.xwx.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/quiz")
public class QuizChatController {

    @Autowired
    private QuizChatService quizChatService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private AnswerService answerService;

    // 初始化测验，获取题目列表并生成GPT开场白
    @PostMapping("/initiateQuiz")
    public ResponseEntity<?> initiateQuiz(@RequestParam String quizNumber, @RequestParam String userId) {
        // 调用服务层获取题目列表，并根据第一道题生成GPT开场白
        Map<String, Object> quizData = quizChatService.initiateQuiz(quizNumber, userId);

        // 构建返回的消息列表
        List<Map<String, String>> messages = new ArrayList<>();

        // 加入GPT的开场白
        String openingMessage = (String) quizData.get("openingMessage");
        messages.add(Map.of("sender", "AI", "content", openingMessage));

        // 加入第一道题目
        /*String firstQuestion = (String) quizData.get("firstQuestion");
        messages.add(Map.of("sender", "System", "content", firstQuestion));*/

        return ResponseEntity.ok(Map.of("messages", messages));
    }

    // 提交学生答案并通过GPT评估当前问题并给出反馈
    @PostMapping("/submitAnswer")
    public ResponseEntity<?> submitAnswer(@RequestBody AnswerRequest answerRequest) {
        // 调用服务处理用户提交的答案并获取下一步的响应
        Map<String, Object> result = quizChatService.processAnswer(answerRequest);

        // 构建返回的消息列表
        List<Map<String, String>> messages = new ArrayList<>();

        // 添加GPT对当前问题的评估
        String evaluation = (String) result.get("evaluation");
        messages.add(Map.of("sender", "AI", "content", evaluation));

        // 检查是否有下一道题目
        String nextQuestion = (String) result.get("nextQuestion");
        if (nextQuestion != null) {
            /*messages.add(Map.of("sender", "System", "content", nextQuestion));*/
        } else {
            // 如果测验结束，显示总结反馈和正确率
            String finalFeedback = (String) result.get("finalFeedback");
            //messages.add(Map.of("sender", "AI", "content", finalFeedback));
        }

        return ResponseEntity.ok(Map.of("messages", messages));
    }

    @PostMapping("/getQuizData")
    public ResponseEntity<Map<String, Object>> getQuizData(@RequestParam String quizNumber, @RequestParam String userId) {
        List<AnswerRequest> answers = answerService.getAnswersWithMaxTimes(quizNumber, userId);
        List<Quiz> questions = quizService.getQuestionsByQuizNumber(quizNumber);
        // 计算平均分
        double averageScore = answerService.getAverageScoreByQuizNumber(quizNumber, userId);
        // 计算等级
        String grade = answerService.calculateGrade(averageScore);

        Map<String, Object> response = new HashMap<>();
        response.put("answers", answers);
        response.put("questions", questions);
        response.put("averageScore", averageScore);  // 返回平均分
        response.put("grade", grade);  // 返回等级

        return ResponseEntity.ok(response);
    }


}
