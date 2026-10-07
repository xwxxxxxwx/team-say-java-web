package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.AnswerRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AnswerMapper {

    @Select("SELECT answer " +
            "FROM answers " +
            "WHERE quiz_number = #{quizNumber} " +
            "  AND user_id = #{userId} " +
            "  AND times = (SELECT MAX(times) FROM answers WHERE quiz_number = #{quizNumber} AND user_id = #{userId})")
    List<AnswerRequest> getAnswersByQuizNumberAndUserIdWithMaxTimes(@Param("quizNumber") String quizNumber, @Param("userId") String userId);

    @Select("SELECT score FROM answers " +
            "WHERE quiz_number = #{quizNumber} AND user_id = #{userId} " +
            "AND times = (SELECT MAX(times) FROM answers WHERE quiz_number = #{quizNumber} AND user_id = #{userId})")
    List<Double> getScoresByQuizNumberAndUserIdWithMaxTimes(@Param("quizNumber") String quizNumber, @Param("userId") String userId);


}

