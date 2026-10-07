package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Feedback;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface FeedbackMapper {

    @Insert("INSERT INTO feedback (course_id, rating, comment, created_at) VALUES (#{courseId}, #{rating}, #{comment}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertFeedback(Feedback feedback);
}