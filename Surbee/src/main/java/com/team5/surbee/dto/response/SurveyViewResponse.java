package com.team5.surbee.dto.response;

import java.util.List;

public record SurveyViewResponse(
        Integer surveyId,
        String title,
        String description,
        List<QuestionResponse> questions
) {
}
