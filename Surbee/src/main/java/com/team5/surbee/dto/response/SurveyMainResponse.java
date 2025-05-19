package com.team5.surbee.dto.response;

import java.util.List;

import com.team5.surbee.dto.request.SurveyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyMainResponse {
    private List<SurveyDto> activeSurveys;
    private List<SurveyDto> closedSurveys;
    private List<SurveyDto> popularSurveys;
}
