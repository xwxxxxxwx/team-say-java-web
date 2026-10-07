package com.xwx.xwx.service;

import com.xwx.xwx.mapper.QuizAnalysisMapper;
import com.xwx.xwx.pojo.ScoreDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class QuizAnalysisService {

    @Autowired
    private QuizAnalysisMapper quizAnalysisMapper;

    // 根据 courseId 获取所有 quiz_number
    public List<String> getQuizNumbersByCourseId(String courseId) {
        return quizAnalysisMapper.findQuizNumbersByCourseId(courseId);
    }

    // 根据 quiz_number 获取所有 question_order
    public List<String> getQuestionsByQuizNumber(String quizNumber) {
        return quizAnalysisMapper.findQuestionsByQuizNumber(quizNumber);
    }

    // 根据 quizNumber 和 questionOrder 获取对应的统计数据
    public Map<String, Object> getQuizStatistics(String quizNumber, String questionOrder) {
        // 如果 questionOrder 是 "All"，计算所有 times 下的总分
        if ("All".equalsIgnoreCase(questionOrder)) {
            return quizAnalysisMapper.findQuizStatisticsForAll(quizNumber);
        } else {
            // 否则计算特定题目的分数数据
            return quizAnalysisMapper.findQuizStatisticsForSpecificQuestion(quizNumber, Integer.parseInt(questionOrder));
        }
    }

    public double getPassRate(String quizNumber, String questionOrder) {
        int questionOrderInt = questionOrder.equals("All") ? -1 : Integer.parseInt(questionOrder);
        int totalAnswers;
        int correctAnswers;

        // 查询总人数和正确人数
        if (questionOrderInt == -1) {
            // 如果是 "All"，则计算所有题目的总人数和正确人数
            totalAnswers = quizAnalysisMapper.countTotalAnswersForAllQuestions(quizNumber);
            correctAnswers = quizAnalysisMapper.countCorrectAnswersForAllQuestions(quizNumber);
        } else {
            totalAnswers = quizAnalysisMapper.countTotalAnswers(quizNumber, questionOrderInt);
            correctAnswers = quizAnalysisMapper.countCorrectAnswers(quizNumber, questionOrderInt);
        }

        return totalAnswers == 0 ? 0.0 : (double) correctAnswers / totalAnswers;
    }

    public ScoreDistribution calculateScoreDistribution(String quizNumber, String questionOrder) {
        if ("All".equalsIgnoreCase(questionOrder)) {
            ScoreDistribution distribution = quizAnalysisMapper.getScoreDistributionForAll(quizNumber);
            return distribution;
        } else {
            ScoreDistribution distribution = quizAnalysisMapper.getScoreDistributionForQuestion(quizNumber, Integer.parseInt(questionOrder));
            return distribution;
        }
    }

}
