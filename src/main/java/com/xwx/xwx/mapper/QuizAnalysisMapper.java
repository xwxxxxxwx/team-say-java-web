package com.xwx.xwx.mapper;

import com.xwx.xwx.pojo.ScoreDistribution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface QuizAnalysisMapper {

    // 查询 menu 表中指定 courseId 且 quiz_number 不为 null 或空字符串的所有 quiz_number
    @Select("SELECT quiz_number FROM menu WHERE course_id = #{courseId} AND quiz_number IS NOT NULL AND quiz_number != ''")
    List<String> findQuizNumbersByCourseId(String courseId);

    // 查询 answers 表中指定 quiz_number 且不为 null 的所有 question_order
    @Select("SELECT DISTINCT question_order FROM answers WHERE quiz_number = #{quizNumber} AND question_order IS NOT NULL")
    List<String> findQuestionsByQuizNumber(String quizNumber);

    // 计算每个用户在每次 times 下的总分，并返回最高分、最低分和平均分
    @Select("SELECT MAX(total_score) AS max_score, MIN(total_score) AS min_score, AVG(total_score) AS average_score " +
            "FROM (SELECT user_id, times, SUM(score) AS total_score FROM answers WHERE quiz_number = #{quizNumber} GROUP BY user_id, times) AS user_scores")
    Map<String, Object> findQuizStatisticsForAll(String quizNumber);

    // 查询某个 quiz_number 下特定 question_order 的最高分、最低分和平均分
    @Select("SELECT MAX(score) AS max_score, MIN(score) AS min_score, AVG(score) AS average_score " +
            "FROM answers WHERE quiz_number = #{quizNumber} AND question_order = #{questionOrder}")
    Map<String, Object> findQuizStatisticsForSpecificQuestion(String quizNumber, int questionOrder);

    // 查询某个 quiz_number 和 question_order 下得分 >= 6 的总人数（表示及格的回答人数）
    @Select("SELECT COUNT(user_id) FROM answers WHERE quiz_number = #{quizNumber} AND question_order = #{questionOrder} AND score >= 6")
    int countCorrectAnswers(@Param("quizNumber") String quizNumber, @Param("questionOrder") int questionOrder);

    // 查询某个 quiz_number 和 question_order 下的总回答人数
    @Select("SELECT COUNT(user_id) FROM answers WHERE quiz_number = #{quizNumber} AND question_order = #{questionOrder}")
    int countTotalAnswers(@Param("quizNumber") String quizNumber, @Param("questionOrder") int questionOrder);

    // 针对 "All" 情况下的正确回答人数查询，统计所有题目中所有用户至少有一次答题得分 >= 6 的情况
    @Select("SELECT COUNT(DISTINCT CONCAT(user_id, '_', times)) AS correct_answers\n" +
            "FROM (\n" +
            "    SELECT user_id, times, SUM(score) AS total_score, COUNT(question_order) AS total_questions\n" +
            "    FROM answers\n" +
            "    WHERE quiz_number = #{quizNumber}\n" +
            "    GROUP BY user_id, times\n" +
            ") AS user_scores\n" +
            "WHERE total_score >= (6 * total_questions);\n")
    int countCorrectAnswersForAllQuestions(@Param("quizNumber") String quizNumber);

    // 针对 "All" 情况下的总答题人数查询，统计所有题目的总答题人数
    @Select("SELECT COUNT(DISTINCT CONCAT(user_id, '_', times)) AS total_answers\n" +
            "FROM answers\n" +
            "WHERE quiz_number = #{quizNumber};")
    int countTotalAnswersForAllQuestions(@Param("quizNumber") String quizNumber);

    @Select("SELECT \n" +
            "  COALESCE(SUM(CASE WHEN score = 10 THEN 1 ELSE 0 END), 0) AS A,\n" +
            "  COALESCE(SUM(CASE WHEN score >= 9 AND score < 10 THEN 1 ELSE 0 END), 0) AS B,\n" +
            "  COALESCE(SUM(CASE WHEN score >= 8 AND score < 9 THEN 1 ELSE 0 END), 0) AS C,\n" +
            "  COALESCE(SUM(CASE WHEN score >= 7 AND score < 8 THEN 1 ELSE 0 END), 0) AS D,\n" +
            "  COALESCE(SUM(CASE WHEN score >= 6 AND score < 7 THEN 1 ELSE 0 END), 0) AS E,\n" +
            "  COALESCE(SUM(CASE WHEN score < 6 THEN 1 ELSE 0 END), 0) AS F\n" +
            "FROM answers \n" +
            "WHERE quiz_number = #{quizNumber} AND question_order = #{questionOrder};\n")
    ScoreDistribution getScoreDistributionForQuestion(@Param("quizNumber") String quizNumber, @Param("questionOrder") int questionOrder);

    @Select("SELECT \n" +
            "    SUM(CASE WHEN total_percentage = 100 THEN 1 ELSE 0 END) AS A,\n" +
            "    SUM(CASE WHEN total_percentage >= 90 AND total_percentage < 100 THEN 1 ELSE 0 END) AS B,\n" +
            "    SUM(CASE WHEN total_percentage >= 80 AND total_percentage < 90 THEN 1 ELSE 0 END) AS C,\n" +
            "    SUM(CASE WHEN total_percentage >= 70 AND total_percentage < 80 THEN 1 ELSE 0 END) AS D,\n" +
            "    SUM(CASE WHEN total_percentage >= 60 AND total_percentage < 70 THEN 1 ELSE 0 END) AS E,\n" +
            "    SUM(CASE WHEN total_percentage < 60 THEN 1 ELSE 0 END) AS F\n" +
            "FROM (\n" +
            "    SELECT user_id, times, (SUM(score) / (COUNT(question_order) * 10)) * 100 AS total_percentage\n" +
            "    FROM answers \n" +
            "    WHERE quiz_number = #{quizNumber}\n" +
            "    GROUP BY user_id, times\n" +
            ") AS user_scores;\n")
    ScoreDistribution getScoreDistributionForAll(@Param("quizNumber") String quizNumber);

}
