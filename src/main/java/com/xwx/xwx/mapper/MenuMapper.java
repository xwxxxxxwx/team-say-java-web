package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Menu;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MenuMapper {

    @Insert("INSERT INTO menu (course_id, menu_node, parent_id, category, description, quiz_number, click_count) " +
            "VALUES (#{courseId}, #{menuNode}, #{parentId}, #{category}, #{description}, #{quizNumber}, #{clickCount})")
    @Options(useGeneratedKeys = true, keyProperty = "nodeId")
    void insertMenu(Menu menu);

    @Select("SELECT node_id FROM menu WHERE course_id = #{courseId} AND menu_node = #{menuNode} LIMIT 1")
    Integer findNodeIdByMenuNode(@Param("courseId") String courseId, @Param("menuNode") String menuNode);

    @Delete("DELETE FROM menu WHERE course_id = #{courseId}")
    void deleteByCourseId(@Param("courseId") String courseId);

    @Select("SELECT * FROM menu WHERE node_id = #{nodeId} AND course_id = #{courseId}")
    Menu findByNodeIdAndCourseId(@Param("nodeId") int nodeId, @Param("courseId") String courseId);

    @Update("UPDATE menu SET click_count = click_count + 1 WHERE node_id = #{nodeId} AND course_id = #{courseId}")
    void incrementClickCount(@Param("nodeId") int nodeId, @Param("courseId") String courseId);

    @Select("SELECT menu_node FROM menu WHERE quiz_number = #{quizNumber}")
    String findMenuNodeByQuizNumber(@Param("quizNumber") String quizNumber);
}