package com.team5.surbee.dto.response.survey;

import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.constant.QuestionType;

import java.util.List;

public record QuestionResponse(
        Integer id,
        String questionText,
        QuestionType questionType,
        boolean isRequired,
        List<String> options
) {
    public static QuestionResponse from(Question question) {
        return new QuestionResponse(
                question.getId(),
                question.getQuestionText(),
                question.getQuestionType(),
                question.getIsRequired(),
                question.getOptions().stream()
                        .map(Option::getOptionText)
                        .toList()
        );
    }
}
