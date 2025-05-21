package com.team5.surbee.dto;

import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.Survey;
import com.team5.surbee.entity.constant.QuestionType;

import java.util.List;

public record QuestionDto(
        Integer id,
        String questionText,
        QuestionType questionType,
        boolean isRequired,
        List<OptionDto> options
) {
    public static QuestionDto from(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getQuestionText(),
                question.getQuestionType(),
                question.getIsRequired(),
                question.getOptions().stream().map(OptionDto::from).toList()
        );
    }

    public Question toEntity(Survey survey, List<Option> optionEntities) {
        boolean hasOptions = questionType == QuestionType.MULTIPLE_CHOICE || questionType == QuestionType.CHECKBOX;
        List<Option> finalOptions = hasOptions ? optionEntities : List.of();
        return Question.of(questionText, questionType, isRequired, survey, finalOptions);
    }
}
