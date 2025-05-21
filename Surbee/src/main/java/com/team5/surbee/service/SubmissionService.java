package com.team5.surbee.service;

import com.team5.surbee.dto.SessionUserDto;
import com.team5.surbee.dto.request.AnswerRequest;
import com.team5.surbee.dto.request.SurveyAnswerRequest;
import com.team5.surbee.entity.*;
import com.team5.surbee.repository.AnswerRepository;
import com.team5.surbee.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    // Answer 관련 처리는 여기
    private final SubmissionRepository submissionRepository;
    private final AnswerRepository answerRepository;

    private final SurveyService surveyService;

    @Transactional
    public void saveSubmission(SurveyAnswerRequest request, SessionUserDto user) {
        Survey survey = surveyService.getSurveyOrThrow(request.surveyId());

        List<Answer> answers = new ArrayList<>();

        for (AnswerRequest answerReq : request.answers()) {
            Question question = surveyService.getQuestionOrThrow(answerReq.questionId());

            if (question.getQuestionType().isText()) {
                // SHORT_ANSWER / LONG_ANSWER
                answers.add(Answer.textAnswer(question, answerReq.answerText(), null));
            } else {
                // MULTIPLE_CHOICE / CHECKBOX
                for (Integer optionId : answerReq.selectedOptionIds()) {
                    Option option = surveyService.getOptionOrThrow(optionId);
                    answers.add(Answer.optionAnswer(question, option, null));
                }
            }
        }

        // user가 null이면 userId도 null로 설정
        Integer userId = (user != null) ? user.id() : null;

        // Submission 생성 및 연관관계 설정
        Submission submission = Submission.of(userId, survey, answers);
        answers.forEach(answer -> answer.assignToSubmission(submission));

        // Survey의 응답 수 증가
        survey.increaseSubmissionCount(); // 아래에 해당 메서드 구현 필요

        submissionRepository.save(submission);
    }
}
