package com.team5.surbee.dto.request;

import com.team5.surbee.entity.Survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyDto {
    private Integer id;
    private String title;
    private boolean isClosed;
    private int submissionCount;

    public static SurveyDto fromEntity(Survey survey) {
        return SurveyDto.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .isClosed(survey.isClosed())
                .submissionCount(survey.getSubmissionCount())
                .build();
    }
}



