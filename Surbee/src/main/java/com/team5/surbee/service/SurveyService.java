package com.team5.surbee.service;

import com.team5.surbee.common.exception.ErrorCode;
import com.team5.surbee.common.exception.SurveyException;
import com.team5.surbee.common.exception.UserExcpetion;
import com.team5.surbee.dto.SurveyDto;
import com.team5.surbee.dto.response.SurveyMainResponse;
import com.team5.surbee.dto.response.SurveyVoteResponse;
import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.Survey;
import com.team5.surbee.entity.User;
import com.team5.surbee.repository.OptionRepository;
import com.team5.surbee.repository.QuestionRepository;
import com.team5.surbee.repository.SurveyRepository;
import com.team5.surbee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {
    // Question, Option은 여기서 처리
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    public SurveyMainResponse getMainSurveyList() {
        List<SurveyDto> active = surveyRepository.findTop10ByIsClosedFalseOrderByCreatedAtDesc()
                .stream().map(SurveyDto::from).toList();

        List<SurveyDto> closed = surveyRepository.findTop10ByIsClosedTrueOrderByCreatedAtDesc()
                .stream().map(SurveyDto::from).toList();

        List<SurveyDto> popular = surveyRepository.findTop10ByOrderBySubmissionCountDesc()
                .stream().map(SurveyDto::from).toList();

        return SurveyMainResponse.builder()
                .activeSurveys(active)
                .closedSurveys(closed)
                .popularSurveys(popular)
                .build();
    }

    @Transactional
    public void createSurvey(SurveyDto surveyDto) {
        // 1. 유저 조회
        User user = userRepository.findById(surveyDto.user().id())
                .orElseThrow(() -> new UserExcpetion(ErrorCode.USER_NOT_FOUND));

        // 2. 질문 + 옵션 엔티티 생성
        List<Question> questionEntities = surveyDto.questions().stream()
                .map(questionDto -> {
                    List<Option> optionEntities = questionDto.options().stream()
                            .map(optionDto -> optionDto.toEntity(null)) // Question은 아래에서 연결
                            .toList();

                    Question question = questionDto.toEntity(null, optionEntities);

                    // Option → Question 역참조 설정
                    for (Option option : optionEntities) {
                        option.assignToQuestion(question);
                    }

                    return question;
                })
                .toList();

        // 3. 설문 엔티티 생성
        Survey survey = surveyDto.toEntity(user, questionEntities);

        // Question → Survey 역참조 설정
        for (Question question : questionEntities) {
            question.assignToSurvey(survey);
        }

        // 4. 저장
        surveyRepository.save(survey);
    }

    @Transactional(readOnly = true)
    public SurveyVoteResponse getSurveyVote(Integer id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new SurveyException(ErrorCode.SURVEY_NOT_FOUND));

        return SurveyVoteResponse.from(survey);
    }
}
