package com.team5.surbee.dto.request;

import com.team5.surbee.dto.OptionDto;

public record OptionRequest(
        String optionText
) {
    public OptionDto toDto() {
        return new OptionDto(null, optionText);
    }
}
