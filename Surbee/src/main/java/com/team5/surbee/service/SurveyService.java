package com.team5.surbee.service;

import com.team5.surbee.dto.request.SurveyDto;
import com.team5.surbee.dto.response.SurveyMainResponse;
import com.team5.surbee.repository.OptionRepository;
import com.team5.surbee.repository.QuestionRepository;
import com.team5.surbee.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {
    // Question, Option은 여기서 처리
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

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
