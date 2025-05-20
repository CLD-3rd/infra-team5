package com.team5.surbee.dto.response.survey;

import com.team5.surbee.entity.Survey;

import java.time.LocalDateTime;

public record SurveySummaryResponse(
        Integer id,
        String title,
        boolean isPublic,
        boolean isClosed,
        Integer submissionCount,
        LocalDateTime createdAt
) {
    public static SurveySummaryResponse from(Survey survey) {
        return new SurveySummaryResponse(
                survey.getId(),
                survey.getTitle(),
                survey.isPublic(),
                survey.isClosed(),
                survey.getSubmissionCount(),
                survey.getCreatedAt()
        );
    }
}
