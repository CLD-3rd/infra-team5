package com.team5.surbee.dto.response.survey;

import lombok.Builder;

import java.util.List;

@Builder
public record SurveyMainResponse(
        List<SurveySummaryResponse> activeSurveys,
        List<SurveySummaryResponse> closedSurveys,
        List<SurveySummaryResponse> popularSurveys
) {
}
