package com.team5.surbee.dto.response.survey;

import java.util.List;

public record SurveyResultResponse(
        Integer id,
        String title,
        String description,
        List<QuestionResultResponse> questions
) {
}
