package com.xwx.xwx.service;

import com.xwx.xwx.pojo.Quiz;
import com.xwx.xwx.mapper.QuizMapper;
import com.xwx.xwx.pojo.QuizResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuizMapper quizMapper;

    public void saveQuiz(Quiz quiz) {
        quizMapper.insertQuiz(quiz);
    }

    public void deleteByQuizNumber(String quizNumber) {
        quizMapper.deleteByQuizNumber(quizNumber);
    }

    public void deleteByCourseId(String courseId) {
        quizMapper.deleteByCourseId(courseId);
    }

    public List<Quiz> findByCourseId(String courseId) {
        return quizMapper.findByCourseId(courseId);
    }

    public int findMaxQuestionOrder(String quizNumber) {
        Integer maxOrder = quizMapper.findMaxQuestionOrder(quizNumber);
        return maxOrder != null ? maxOrder : 0;
    }

    public String findQuestionByOrder(String quizNumber, int questionOrder) {
        return quizMapper.findQuestionByOrder(quizNumber, questionOrder);
    }

    public String findAnswerByOrder(String quizNumber, int questionOrder) {
        return quizMapper.findAnswerByOrder(quizNumber, questionOrder);
    }

    /*public void saveAllAnswers(List<AnswerRequest> answers) {
        for (AnswerRequest answer : answers) {
            quizMapper.saveAnswer(answer.getQuizNumber(), answer.getQuestionOrder(), answer.getAnswer(), answer.getUserId());
        }
    }*/

    public List<QuizResult> getQuizResults(String quizNumber, String userId, int times) {
        return quizMapper.getQuizResults(quizNumber, userId, times);
    }

    public List<Quiz> getQuizzesByQuizNumber(String quizNumber) {
        return quizMapper.getQuizzesByQuizNumber(quizNumber);
    }

    // 保存答案
    public void saveAnswer(String quizNumber, int questionOrder, String answer, String userId, int times, Double score ) {
        quizMapper.insertAnswer(userId, quizNumber, questionOrder, answer, times, score);
    }

    public Integer getMaxTimes(String userId, String quizNumber) {
        return quizMapper.getMaxTimes(userId, quizNumber);
    }

    //cc 10-09
    public String getAiApiByQuizNumber(String quizNumber) {
        // 使用CourseMapper调用数据库查询
        return quizMapper.findByQuizNumber(quizNumber);
    }

    public List<Quiz> getQuestionsByQuizNumber(String quizNumber) {
        return quizMapper.getQuestionByQuizNumber(quizNumber);
    }
}
