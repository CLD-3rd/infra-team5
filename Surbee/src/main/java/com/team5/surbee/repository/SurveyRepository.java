package com.team5.surbee.repository;

import com.team5.surbee.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findTop10ByIsClosedFalseOrderByCreatedAtDesc();

    List<Survey> findTop10ByIsClosedTrueOrderByCreatedAtDesc();

    List<Survey> findTop10ByOrderBySubmissionCountDesc();
}

