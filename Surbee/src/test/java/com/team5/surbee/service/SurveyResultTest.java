package com.team5.surbee.service;

import com.team5.surbee.common.exception.ErrorCode;
import com.team5.surbee.common.exception.UserExcpetion;
import com.team5.surbee.dto.response.survey.SurveyResultResponse;
import com.team5.surbee.entity.*;
import com.team5.surbee.entity.constant.QuestionType;
import com.team5.surbee.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback(false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SurveyResultTest {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SurveyService surveyService;

    private Survey survey;

    @BeforeEach
    void setUp() {
        // 사용자 생성
        User user = userRepository.findById(1).orElseThrow(() -> new UserExcpetion(ErrorCode.USER_NOT_FOUND));

        // 설문 + 질문 + 옵션 세팅
        survey = Survey.of(
                "프로그래밍 선호도 조사",
                "개발자 대상으로 선호 기술 조사",
                true,
                null,
                user,
                new ArrayList<>()
        );
        survey = surveyRepository.save(survey);

        // 질문 4개: MC / CHECKBOX / SHORT / LONG
        for (QuestionType type : QuestionType.values()) {
            Question q = Question.of("질문 - " + type.name(), type, true, survey, new ArrayList<>());
            q = questionRepository.save(q);
            survey.getQuestions().add(q);
            // 객관식 질문에는 옵션 추가
            if (type == QuestionType.MULTIPLE_CHOICE || type == QuestionType.CHECKBOX) {
                for (int i = 1; i <= 3; i++) {
                    Option opt = Option.of("옵션 " + i, q);
                    q.getOptions().add(opt);
                    optionRepository.save(opt);
                }
            }
        }
    }

    @Test
    void generateDummyResponsesAndTestSurveyResult() {
        List<Question> questions = questionRepository.findAllBySurveyId(survey.getId());

        for (int i = 0; i < 100; i++) {
            List<Answer> answers = new ArrayList<>();

            for (Question q : questions) {
                if (q.getQuestionType() == QuestionType.MULTIPLE_CHOICE || q.getQuestionType() == QuestionType.CHECKBOX) {
                    // 무작위 옵션 선택
                    List<Option> options = q.getOptions();
                    Option randomOption = options.get(i % options.size());
                    answers.add(Answer.optionAnswer(q, randomOption, null));
                } else {
                    // 주관식 응답
                    answers.add(Answer.textAnswer(q, "응답 내용 #" + i, null));
                }
            }

            Submission s = Submission.of(1, survey, answers); // userId = 1
            submissionRepository.save(s);

            // submission을 각 answer에 set (역참조)
            for (Answer a : answers) {
                a.assignToSubmission(s);
                answerRepository.save(a);
            }
        }

        // 실제 서비스 로직 테스트
        SurveyResultResponse result = surveyService.getSurveyResult(survey.getId());

        // 결과 검증
        assertEquals(4, result.questions().size());
        System.out.println(result);
    }
}