package com.team5.surbee.dto;

import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;
import com.team5.surbee.entity.Survey;
import com.team5.surbee.entity.constant.QuestionType;

import java.util.List;
import java.util.stream.Collectors;

public record QuestionDto(
        Integer id,
        String questionText,
        QuestionType questionType,
        Boolean isRequired,
        List<OptionDto> options
) {
    public static QuestionDto from(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getQuestionText(),
                question.getQuestionType(),
                question.getIsRequired(),
                question.getOptions().stream().map(OptionDto::from).collect(Collectors.toList())
        );
    }

    public Question toEntity(Survey survey, List<Option> optionEntities) {
        return Question.of(questionText, questionType, isRequired, survey, optionEntities);
    }
}
