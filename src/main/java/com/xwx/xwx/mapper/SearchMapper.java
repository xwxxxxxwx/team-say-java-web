package com.xwx.xwx.mapper;
import com.xwx.xwx.pojo.Search;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
@Mapper
public interface SearchMapper {

    @Select("SELECT * FROM course WHERE course_name LIKE CONCAT('%', #{query}, '%') OR course_id LIKE CONCAT('%', #{query}, '%')")
    List<Search> searchCourses(@Param("query") String query);
}

