package com.team5.surbee.repository;

import com.team5.surbee.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAllBySurveyId(Integer surveyId);
}
