package com.team5.surbee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team5.surbee.dto.request.SurveyDto;
import com.team5.surbee.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findTop10ByIsClosedFalseOrderByCreatedAtDesc();
    List<Survey> findTop10ByIsClosedTrueOrderByCreatedAtDesc();
    List<Survey> findTop10ByOrderBySubmissionCountDesc();
}

