package com.team5.surbee.dto.response;

import com.team5.surbee.dto.SurveyDto;
import lombok.*;

import java.util.List;

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
