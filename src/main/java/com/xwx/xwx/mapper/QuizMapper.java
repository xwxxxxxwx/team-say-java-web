package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.Quiz;
import com.xwx.xwx.pojo.QuizResult;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuizMapper {

    @Insert("INSERT INTO quiz (quiz_number, question_order, question, answer, question_type, created_at) " +
            "VALUES (#{quizNumber}, #{questionOrder}, #{question}, #{answer}, #{questionType}, #{createdAt})")
    void insertQuiz(Quiz quiz);

    @Delete("DELETE FROM quiz WHERE quiz_number = #{quizNumber}")
    void deleteByQuizNumber(@Param("quizNumber") String quizNumber);

    @Delete("DELETE FROM quiz WHERE quiz_number LIKE CONCAT(#{courseId}, '-%')")
    void deleteByCourseId(@Param("courseId") String courseId);


    @Select("SELECT * FROM quiz WHERE quiz_number LIKE CONCAT(#{courseId}, '%')")
    List<Quiz> findByCourseId(@Param("courseId") String courseId);

    @Select("SELECT MAX(question_order) FROM quiz WHERE quiz_number = #{quizNumber}")
    Integer findMaxQuestionOrder(@Param("quizNumber") String quizNumber);

    @Select("SELECT question FROM quiz WHERE quiz_number = #{quizNumber} AND question_order = #{questionOrder}")
    String findQuestionByOrder(@Param("quizNumber") String quizNumber, @Param("questionOrder") int questionOrder);

    @Select("SELECT answer FROM quiz WHERE quiz_number = #{quizNumber} AND question_order = #{questionOrder}")
    String findAnswerByOrder(@Param("quizNumber") String quizNumber, @Param("questionOrder") int questionOrder);


    /*@Insert("INSERT INTO answers (quiz_number, question_order, answer, user_id) VALUES (#{quizNumber}, #{questionOrder}, #{answer}, #{userId})")
    void saveAnswer(@Param("quizNumber") String quizNumber, @Param("questionOrder") int questionOrder, @Param("answer") String answer, @Param("userId") String userId);*/

    @Select("SELECT q.question, q.answer AS correctAnswer, a.answer AS studentAnswer, a.times " +
            "FROM quiz q " +
            "LEFT JOIN answers a ON q.quiz_number = a.quiz_number AND q.question_order = a.question_order " +
            "WHERE q.quiz_number = #{quizNumber} AND a.user_id = #{userId} AND a.times = #{times}")
    List<QuizResult> getQuizResults(@Param("quizNumber") String quizNumber, @Param("userId") String userId, @Param("times") int times);


    //hsh 找quizNumber对应的信息
    @Select("SELECT id, question_order, question, answer, question_type FROM quiz WHERE quiz_number = #{quizNumber}")
    List<Quiz> getQuizzesByQuizNumber(String quizNumber);

    @Select("SELECT MAX(times) FROM answers WHERE user_id = #{userId} AND quiz_number = #{quizNumber}")
    Integer getMaxTimes(@Param("userId") String userId, @Param("quizNumber") String quizNumber);


    @Insert("INSERT INTO answers (user_id, quiz_number, question_order, answer, times, score) " +
            "VALUES (#{userId}, #{quizNumber}, #{questionOrder}, #{answer}, #{times}, #{score})")
    void insertAnswer(@Param("userId") String userId, @Param("quizNumber") String quizNumber,
                      @Param("questionOrder") int questionOrder, @Param("answer") String answer, @Param("times") int times, @Param("score") Double score);

    //cc 10-09
    @Select("SELECT ai_api FROM course WHERE course_id = SUBSTRING_INDEX(#{quizNumber}, '-', 1)")
    String findByQuizNumber(@Param("quizNumber") String quizNumber);

    //cc 找quizNumber对应的信息
    @Select("SELECT question FROM quiz WHERE quiz_number = #{quizNumber}")
    List<Quiz> getQuestionByQuizNumber(String quizNumber);
}
