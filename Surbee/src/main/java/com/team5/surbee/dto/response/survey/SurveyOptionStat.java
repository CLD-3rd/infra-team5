package com.team5.surbee.dto.response.survey;

public record SurveyOptionStat(
        Integer optionId,
        String optionText,
        Long count
) {
}
