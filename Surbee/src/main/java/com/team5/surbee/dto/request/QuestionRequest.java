package com.team5.surbee.dto.request;

import com.team5.surbee.dto.OptionDto;
import com.team5.surbee.dto.QuestionDto;
import com.team5.surbee.entity.constant.QuestionType;

import java.util.List;

public record QuestionRequest(
        String questionText,
        QuestionType type,
        Boolean isRequired,
        List<OptionRequest> options
) {
    public boolean getIsRequiredSafe() {
        return Boolean.TRUE.equals(isRequired);
    }

    public QuestionDto toDto() {
        List<OptionDto> optionDtos = options == null
                ? List.of()
                : options.stream().map(OptionRequest::toDto).toList();
        return new QuestionDto(
                null,
                questionText,
                type,
                getIsRequiredSafe(),
                optionDtos
        );
    }
}
