package com.xwx.xwx.pojo;

public class AnswerRequest {

    private String userId;      // 用户ID
    private String quizNumber;  // 测验编号
    private int questionOrder;  // 问题顺序
    private String answer;      // 答案
    private int times;          // 回答次数 (新增加的属性)
    private int score;

    // 无参构造器
    public AnswerRequest() {
    }

    // 有参构造器
    public AnswerRequest(String userId, String quizNumber, int questionOrder, String answer, int times, int score) {
        this.userId = userId;
        this.quizNumber = quizNumber;
        this.questionOrder = questionOrder;
        this.answer = answer;
        this.times = times;
        this.score = score;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(String quizNumber) {
        this.quizNumber = quizNumber;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "AnswerRequest{" +
                "userId='" + userId + '\'' +
                ", quizNumber='" + quizNumber + '\'' +
                ", questionOrder=" + questionOrder +
                ", answer='" + answer + '\'' +
                ", times=" + times +
                ", score=" + score +
                '}';
    }
}
