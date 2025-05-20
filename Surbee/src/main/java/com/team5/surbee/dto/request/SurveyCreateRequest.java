package com.team5.surbee.dto.request;

import com.team5.surbee.dto.SessionUserDto;
import com.team5.surbee.dto.SurveyDto;

import java.util.List;

public record SurveyCreateRequest(
        String title,
        String description,
        String privacy,
        String password,
        List<QuestionRequest> questions
) {
    public boolean isPublic() {
        return "PUBLIC".equalsIgnoreCase(privacy);
    }

    public SurveyDto toDto(SessionUserDto user) {
        return new SurveyDto(
                null,
                title,
                description,
                isPublic(),
                false,
                password,
                0,
                null,
                user,
                questions.stream().map(QuestionRequest::toDto).toList()
        );
    }

}
