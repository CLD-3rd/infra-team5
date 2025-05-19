package com.team5.surbee.dto.response;

import java.util.List;

public record QuestionResponse(
        Integer questionId,
        String questionText,
        String questionType,
        boolean isRequired,
        List<String> options // SHORT/LONG 타입이면 null or 빈 리스트
) {
}
