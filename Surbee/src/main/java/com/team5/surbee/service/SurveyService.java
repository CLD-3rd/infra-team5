package com.team5.surbee.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team5.surbee.dto.request.SurveyDto;
import com.team5.surbee.dto.response.SurveyMainResponse;
import com.team5.surbee.repository.SurveyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public SurveyMainResponse getMainSurveyList() {
        List<SurveyDto> active = surveyRepository.findTop10ByIsClosedFalseOrderByCreatedAtDesc()
            .stream().map(SurveyDto::fromEntity).toList();

        List<SurveyDto> closed = surveyRepository.findTop10ByIsClosedTrueOrderByCreatedAtDesc()
            .stream().map(SurveyDto::fromEntity).toList();

        List<SurveyDto> popular = surveyRepository.findTop10ByOrderBySubmissionCountDesc()
            .stream().map(SurveyDto::fromEntity).toList();

        return SurveyMainResponse.builder()
                .activeSurveys(active)
                .closedSurveys(closed)
                .popularSurveys(popular)
                .build();
    }

}
