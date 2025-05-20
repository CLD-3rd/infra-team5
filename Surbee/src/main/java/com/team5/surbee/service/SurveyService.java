package com.team5.surbee.service;

import com.team5.surbee.common.exception.ErrorCode;
import com.team5.surbee.common.exception.SurveyException;
import com.team5.surbee.common.exception.UserExcpetion;
import com.team5.surbee.dto.SurveyDto;
import com.team5.surbee.dto.response.survey.SurveyMainResponse;
import com.team5.surbee.dto.response.survey.SurveySummaryResponse;
import com.team5.surbee.dto.response.survey.SurveyVoteResponse;
import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.Survey;
import com.team5.surbee.entity.User;
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

    @Transactional(readOnly = true)
    public SurveyMainResponse getMainSurveyList() {
        List<Survey> active = surveyRepository.findTop10ByIsClosedFalseOrderByCreatedAtDesc();

        List<Survey> closed = surveyRepository.findTop10ByIsClosedTrueOrderByCreatedAtDesc();

        List<Survey> popular = surveyRepository.findTop10ByOrderBySubmissionCountDesc();

        return SurveyMainResponse.builder()
                .activeSurveys(active.stream()
                        .map(SurveySummaryResponse::from).toList())
                .closedSurveys(closed.stream()
                        .map(SurveySummaryResponse::from).toList())
                .popularSurveys(popular.stream()
                        .map(SurveySummaryResponse::from).toList())
                .build();
    }

    @Transactional
    public void createSurvey(SurveyDto surveyDto) {
        User user = getUserOrThrow(surveyDto.user().id());
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
        Survey survey = getSurveyOrThrow(id);

        return SurveyVoteResponse.from(survey);
    }

    @Transactional
    public void deleteSurvey(Integer surveyId, Integer userId) {
        getUserOrThrow(userId);

        Survey survey = getSurveyOrThrow(surveyId);

        if (!survey.getUser().getId().equals(userId)) {
            throw new SurveyException(ErrorCode.SURVEY_DELETE_FORBIDDEN);
        }

        surveyRepository.delete(survey);
    }

    @Transactional(readOnly = true)
    public List<SurveySummaryResponse> getSurveysByUser(Integer userId) {
        User user = getUserOrThrow(userId);

        List<Survey> surveys = surveyRepository.findByUser(user);

        return surveys.stream()
                .map(SurveySummaryResponse::from)
                .toList();
    }

    private User getUserOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserExcpetion(ErrorCode.USER_NOT_FOUND));
    }

    private Survey getSurveyOrThrow(Integer surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyException(ErrorCode.SURVEY_NOT_FOUND));
    }
}
