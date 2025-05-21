package com.team5.surbee.repository;

import com.team5.surbee.entity.Survey;
import com.team5.surbee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    List<Survey> findTop10ByIsClosedFalseAndIsPublicTrueOrderByCreatedAtDesc();

    List<Survey> findTop10ByIsClosedTrueAndIsPublicTrueOrderByCreatedAtDesc();

    List<Survey> findTop10ByIsPublicTrueOrderBySubmissionCountDesc();

    List<Survey> findByUser(User user);

    @Query("SELECT s FROM Survey s JOIN FETCH s.questions WHERE s.id = :id")
    Optional<Survey> findByIdWithQuestions(@Param("id") Integer id);
}

