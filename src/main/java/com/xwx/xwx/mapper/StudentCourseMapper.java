package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Class;
import com.xwx.xwx.pojo.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentCourseMapper {

    @Select("SELECT * FROM course WHERE course_id = #{courseId}")
    Course getCourseById(@Param("courseId") String courseId);

    @Insert("INSERT INTO class (user_id, course_id, enrolled_at) VALUES (#{userId}, #{courseId}, #{enrolledAt})")
    void insertEnrollment(Class enrollment);

    @Select("SELECT course.* FROM course " +
            "JOIN class ON course.course_id = class.course_id " +
            "WHERE class.user_id = #{userId} AND course.is_real = 1")
    List<Course> getCoursesByUserId(@Param("userId") String userId);
}