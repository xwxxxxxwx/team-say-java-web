package com.xwx.xwx.service;

import com.xwx.xwx.mapper.AnswerMapper;
import com.xwx.xwx.pojo.AnswerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    // 获取最大times对应的一组answer
    public List<AnswerRequest> getAnswersWithMaxTimes(String quizNumber, String userId) {
        return answerMapper.getAnswersByQuizNumberAndUserIdWithMaxTimes(quizNumber, userId);
    }

    public double getAverageScoreByQuizNumber(String quizNumber, String userId) {
        List<Double> scores = answerMapper.getScoresByQuizNumberAndUserIdWithMaxTimes(quizNumber, userId);
        if (scores.isEmpty()) {
            return 0.0;
        }
        double sum = scores.stream().mapToDouble(Double::doubleValue).sum();
        return sum / scores.size();
    }

    // 根据平均分计算等级
    public String calculateGrade(double averageScore) {
        if (averageScore >= 10.0) {
            return "A";
        } else if (averageScore >= 9.0) {
            return "B";
        } else if (averageScore >= 8.0) {
            return "C";
        } else if (averageScore >= 7.0) {
            return "D";
        } else if (averageScore >= 6.0) {
            return "E";
        } else {
            return "F";
        }
    }
}


