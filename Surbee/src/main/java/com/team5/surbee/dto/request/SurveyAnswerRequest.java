package com.team5.surbee.dto.request;

import java.util.List;

public record SurveyAnswerRequest(
        Integer surveyId,
        List<AnswerRequest> answers
) {
}
