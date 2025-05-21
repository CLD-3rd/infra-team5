package com.team5.surbee.repository;

import com.team5.surbee.dto.response.survey.SurveyOptionStat;
import com.team5.surbee.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Query("""
                SELECT new com.team5.surbee.dto.response.survey.SurveyOptionStat(
                    o.id, o.optionText, COUNT(a.id)
                )
                FROM Answer a
                JOIN a.option o
                WHERE o.question.id = :questionId
                GROUP BY o.id, o.optionText
            """)
    List<SurveyOptionStat> countAnswersByOption(@Param("questionId") Integer questionId);

    @Query("""
                SELECT a.answerText
                FROM Answer a
                WHERE a.question.id = :questionId AND a.answerText IS NOT NULL
            """)
    List<String> findTextAnswersByQuestionId(@Param("questionId") Integer questionId);
}
