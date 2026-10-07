package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Course;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Insert("INSERT INTO course (course_id, course_name, course_description, course_pin, knowledge_base_link, ai_api, digital_human_api, course_category, teacher_id, is_Real) " +
            "VALUES (#{courseId}, #{courseName}, #{courseDescription}, #{coursePin}, #{knowledgeBaseLink}, #{aiApi}, #{digitalHumanApi}, #{courseCategory}, #{teacherId}, #{isReal})")
    void insertCourse(Course course);

    @Select("SELECT course_id FROM course WHERE course_category = #{category} ORDER BY course_id DESC LIMIT 1")
    String getLastCourseIdByCategory(String category);

    @Select("SELECT * FROM course WHERE teacher_id = #{teacherId}")
    List<Course> getCoursesByTeacherId(Long teacherId);

    @Select("SELECT * FROM course WHERE course_id = #{courseId} AND course_pin = #{coursePin}")
    Course getCourseByIdAndPin(String courseId, String coursePin);

    @Select("SELECT COUNT(*) FROM course WHERE course_id = #{courseId} AND course_pin = #{coursePin}")
    int validateCourse(String courseId, String coursePin);

    @Select("SELECT * FROM course WHERE course_id = #{courseId}")
    Course findById(String courseId);

    @Update("UPDATE course SET is_real = #{isReal} WHERE course_id = #{courseId}")
    void updateCourse(Course course);

    // 新增：根据 courseId 获取课程信息
    @Select("SELECT * FROM course WHERE course_id = #{courseId}")
    Course getCourseById(String courseId);
}