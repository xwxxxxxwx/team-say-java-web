package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Feedback;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReportMapper {

    @Select("SELECT f.*, c.course_category " +
            "FROM feedback f " +
            "JOIN course c ON f.course_id = c.course_id " +
            "WHERE c.teacher_id = #{teacherId} AND c.is_real = 1")
    List<Feedback> getAllFeedbackForTeacher(@Param("teacherId") String teacherId);

    @Select("SELECT f.*, c.course_category " +
            "FROM feedback f " +
            "JOIN course c ON f.course_id = c.course_id " +
            "WHERE c.teacher_id = #{teacherId} AND f.course_id = #{courseId} AND c.is_real = 1")
    List<Feedback> getFeedbackByCourseForTeacher(@Param("teacherId") String teacherId, @Param("courseId") String courseId);

    @Update("UPDATE feedback SET is_read = 1 WHERE id = #{id}")
    void markFeedbackAsRead(@Param("id") int id);

    @Delete("DELETE FROM feedback WHERE id = #{id}")
    void deleteFeedback(@Param("id") int id);
}