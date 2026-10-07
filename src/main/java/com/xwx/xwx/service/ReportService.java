package com.xwx.xwx.service;

import com.xwx.xwx.mapper.ReportMapper;
import com.xwx.xwx.pojo.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;

    public List<Map<String, Object>> getAllFeedback(String teacherId) {
        List<Feedback> feedbackList = reportMapper.getAllFeedbackForTeacher(teacherId);
        return transformFeedbackList(feedbackList);
    }

    public List<Map<String, Object>> getFeedbackByCourse(String teacherId, String courseId) {
        List<Feedback> feedbackList = reportMapper.getFeedbackByCourseForTeacher(teacherId, courseId);
        return transformFeedbackList(feedbackList);
    }

    private List<Map<String, Object>> transformFeedbackList(List<Feedback> feedbackList) {
        return feedbackList.stream().map(feedback -> {
            Map<String, Object> feedbackMap = new HashMap<>();
            feedbackMap.put("id", feedback.getId());
            feedbackMap.put("courseId", feedback.getCourseId());
            feedbackMap.put("rating", feedback.getRating());
            feedbackMap.put("comment", feedback.getComment());
            feedbackMap.put("isRead", feedback.isRead());
            feedbackMap.put("courseCategory", feedback.getCourseCategory());
            feedbackMap.put("createdAt", feedback.getCreatedAt());
            return feedbackMap;
        }).collect(Collectors.toList());
    }



//    public List<Feedback> getAllFeedback() {
//        System.out.println(reportMapper.getAllFeedback());
//        return reportMapper.getAllFeedback();
//    }

//    public List<Feedback> getFeedbackByCourse(String courseId) {
//        return reportMapper.getFeedbackByCourse(courseId);
//    }

    public void markFeedbackAsRead(int id) {
        reportMapper.markFeedbackAsRead(id);
    }

    public void deleteFeedback(int id) {
        reportMapper.deleteFeedback(id);
    }
}