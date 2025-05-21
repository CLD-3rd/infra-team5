package com.team5.surbee.service;

import com.team5.surbee.common.exception.*;
import com.team5.surbee.dto.SurveyDto;
import com.team5.surbee.dto.response.survey.*;
import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.Survey;
import com.team5.surbee.entity.User;
import com.team5.surbee.entity.constant.QuestionType;
import com.team5.surbee.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.team5.surbee.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {
    // Question, Option은 여기서 처리
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;

    @Transactional(readOnly = true)
    public SurveyMainResponse getMainSurveyList() {
        List<Survey> active = surveyRepository.findTop10ByIsClosedFalseAndIsPublicTrueOrderByCreatedAtDesc();

        List<Survey> closed = surveyRepository.findTop10ByIsClosedTrueAndIsPublicTrueOrderByCreatedAtDesc();

        List<Survey> popular = surveyRepository.findTop10ByIsPublicTrueOrderBySubmissionCountDesc();

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

    @Transactional
    public void closeSurvey(Integer surveyId, Integer userId) {
        Survey survey = getSurveyOrThrow(surveyId);

        if (!survey.getUser().getId().equals(userId)) {
            throw new SurveyException(ErrorCode.SURVEY_UPDATE_FORBIDDEN);
        }

        if (survey.isClosed()) {
            throw new SurveyException(ErrorCode.SURVEY_ALREADY_CLOSED);
        }

        survey.setClosed(true);
        surveyRepository.save(survey);
    }


    @Transactional
    public SurveyResultResponse getSurveyResult(Integer surveyId) {
        Survey survey = surveyRepository.findByIdWithQuestions(surveyId)
                .orElseThrow(() -> new RuntimeException(SURVEY_NOT_FOUND.getMessage()));

        List<QuestionResultResponse> questionResults = new ArrayList<>();

        for (Question question : survey.getQuestions()) {
            QuestionResultResponse response;

            if (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE ||
                    question.getQuestionType() == QuestionType.CHECKBOX) {

                List<SurveyOptionStat> stats = answerRepository.countAnswersByOption(question.getId());
                long total = stats.stream().mapToLong(SurveyOptionStat::count).sum();

                List<OptionResultResponse> options = stats.stream()
                        .map(stat -> new OptionResultResponse(
                                stat.optionId(),
                                stat.optionText(),
                                stat.count(),
                                total == 0 ? 0.0 :
                                        BigDecimal.valueOf((double) stat.count() * 100 / total)
                                                .setScale(1, RoundingMode.HALF_UP)
                                                .doubleValue()
                        ))
                        .toList();

                response = new QuestionResultResponse(
                        question.getId(),
                        question.getQuestionText(),
                        question.getQuestionType(),
                        total,
                        options,
                        null
                );

            } else {
                List<String> answers = answerRepository.findTextAnswersByQuestionId(question.getId());

                response = new QuestionResultResponse(
                        question.getId(),
                        question.getQuestionText(),
                        question.getQuestionType(),
                        answers.stream().count(),
                        null,
                        answers
                );
            }

            questionResults.add(response);
        }

        return new SurveyResultResponse(
                survey.getId(),
                survey.getTitle(),
                survey.getDescription(),
                questionResults
        );
    }

    private User getUserOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserExcpetion(ErrorCode.USER_NOT_FOUND));
    }

    protected Survey getSurveyOrThrow(Integer surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> new SurveyException(SURVEY_NOT_FOUND));
    }

    protected Question getQuestionOrThrow(Integer questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionException(QUESTION_NOT_FOUND));
    }

    protected Option getOptionOrThrow(Integer optionId) {
        return optionRepository.findById(optionId)
                .orElseThrow(() -> new OptionException(OPTION_NOT_FOUND));
    }
}
