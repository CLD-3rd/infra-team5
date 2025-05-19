package com.team5.surbee.dto;

import com.team5.surbee.entity.Answer;
import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.Submission;

public record AnswerDto(
        Integer id,
        String answerText,
        OptionDto option
) {
    public static AnswerDto from(Answer answer) {
        return new AnswerDto(
                answer.getId(),
                answer.getAnswerText(),
                answer.getOption() != null ? OptionDto.from(answer.getOption()) : null
        );
    }

    public Answer toEntity(Question question, Option option, Submission submission) {
        return answerText != null ?
                Answer.textAnswer(question, answerText, submission) :
                Answer.optionAnswer(question, option, submission);
    }
}