package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClickCountAnalysisMapper {

    // 根据 courseId 查询 menu 表中相关的目录节点，并按 parent_id 进行排序
    @Select("SELECT * FROM menu WHERE course_id = #{courseId} ORDER BY parent_id, node_id")
    List<Menu> selectMenuByCourseId(String courseId);
}
