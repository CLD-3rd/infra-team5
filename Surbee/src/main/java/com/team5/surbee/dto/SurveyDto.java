package com.team5.surbee.dto;

import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.Survey;
import com.team5.surbee.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public record SurveyDto(
        Integer id,
        String title,
        String description,
        boolean isPublic,
        boolean isClosed,
        String password,
        Integer submissionCount,
        LocalDateTime createdAt,
        SessionUserDto user,
        List<QuestionDto> questions
) {
    public static SurveyDto from(Survey survey) {
        return new SurveyDto(
                survey.getId(),
                survey.getTitle(),
                survey.getDescription(),
                survey.isPublic(),
                survey.isClosed(),
                survey.getPassword(),
                survey.getSubmissionCount(),
                survey.getCreatedAt(),
                SessionUserDto.from(survey.getUser()),
                survey.getQuestions().stream().map(QuestionDto::from).toList()
        );
    }

    public Survey toEntity(User user, List<Question> questionEntities) {
        return Survey.of(title, description, isPublic, password, user, questionEntities);
    }
}