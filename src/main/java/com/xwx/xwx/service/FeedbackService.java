package com.xwx.xwx.service;

import com.xwx.xwx.mapper.FeedbackMapper;
import com.xwx.xwx.pojo.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    public boolean submitFeedback(String courseId, int rating, String comment) {
        Feedback feedback = new Feedback();
        feedback.setCourseId(courseId);  // 更新这里
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setCreatedAt(LocalDateTime.now());

        return feedbackMapper.insertFeedback(feedback) > 0;
    }
}