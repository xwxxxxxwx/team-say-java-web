package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Course;
import com.xwx.xwx.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InCourseMapper {

    @Select("SELECT * FROM course WHERE course_id = #{courseId}")
    Course getCourseById(String courseId);

    @Select("SELECT *, quiz_number as quizNumber FROM menu WHERE course_id = #{courseId} ORDER BY parent_id, category, node_id")
    List<Menu> getCourseMenuById(String courseId);
}