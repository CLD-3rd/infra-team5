package com.team5.surbee.dto;

import com.team5.surbee.entity.Answer;
import com.team5.surbee.entity.Submission;
import com.team5.surbee.entity.Survey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SubmissionDto(
        Integer id,
        Integer userId,
        LocalDateTime submittedAt,
        List<AnswerDto> answers
) {
    public static SubmissionDto from(Submission submission) {
        return new SubmissionDto(
                submission.getId(),
                submission.getUserId(),
                submission.getSubmittedAt(),
                submission.getAnswers().stream().map(AnswerDto::from).collect(Collectors.toList())
        );
    }

    public Submission toEntity(Survey survey, List<Answer> answerEntities) {
        return Submission.of(userId, survey, answerEntities);
    }
}