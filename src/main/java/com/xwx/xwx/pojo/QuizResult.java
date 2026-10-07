package com.xwx.xwx.pojo;

public class QuizResult {
    private String question;          // 问题内容
    private String correctAnswer;     // 标准答案
    private String studentAnswer;     // 学生答案

    // 构造函数
    public QuizResult(String question, String correctAnswer, String studentAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.studentAnswer = studentAnswer;
    }

    // Getters and Setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }
}
