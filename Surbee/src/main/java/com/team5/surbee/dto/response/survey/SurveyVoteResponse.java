package com.team5.surbee.dto.response.survey;

import com.team5.surbee.entity.Survey;

import java.util.List;

public record SurveyVoteResponse(
        Integer id,
        String title,
        String description,
        boolean isPublic,
        boolean isClosed,
        List<QuestionResponse> questions
) {
    public static SurveyVoteResponse from(Survey survey) {
        return new SurveyVoteResponse(
                survey.getId(),
                survey.getTitle(),
                survey.getDescription(),
                survey.isPublic(),
                survey.isClosed(),
                survey.getQuestions().stream()
                        .map(QuestionResponse::from)
                        .toList()
        );
    }
}
