package com.team5.surbee.dto;

import com.team5.surbee.entity.Option;
import com.team5.surbee.entity.Question;

public record OptionDto(
        Integer id,
        String optionText
) {
    public static OptionDto from(Option option) {
        return new OptionDto(option.getId(), option.getOptionText());
    }

    public Option toEntity(Question question) {
        return Option.of(optionText, question);
    }
}
