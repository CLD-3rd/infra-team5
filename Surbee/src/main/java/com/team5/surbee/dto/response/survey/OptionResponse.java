package com.team5.surbee.dto.response.survey;

import com.team5.surbee.entity.Option;

public record OptionResponse(
        Integer id,
        String optionText
) {
    public static OptionResponse from(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getOptionText()
        );
    }
}
