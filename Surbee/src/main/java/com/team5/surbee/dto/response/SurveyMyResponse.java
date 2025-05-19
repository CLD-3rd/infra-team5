package com.team5.surbee.dto.response;

import com.team5.surbee.entity.Survey;

import java.time.LocalDateTime;

public record SurveyMyResponse(
        Integer id,
        String title,
        boolean isClosed,
        LocalDateTime createdAt
) {
    public static SurveyMyResponse from(Survey survey) {
        return new SurveyMyResponse(
                survey.getId(),
                survey.getTitle(),
                survey.isClosed(),
                survey.getCreatedAt()
        );
    }
}
