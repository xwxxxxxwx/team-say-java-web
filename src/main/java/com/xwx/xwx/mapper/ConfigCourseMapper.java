package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ConfigCourseMapper {

    @Select("SELECT * FROM course WHERE course_id = #{courseId}")
    Course selectCourseById(String courseId);

    @Update("UPDATE course SET course_name = #{courseName}, course_description = #{courseDescription}, " +
            "knowledge_base_link = #{knowledgeBaseLink}, ai_api = #{aiApi}, digital_human_api = #{digitalHumanApi} " +
            "WHERE course_id = #{courseId}")
    int updateCourse(Course course);
}