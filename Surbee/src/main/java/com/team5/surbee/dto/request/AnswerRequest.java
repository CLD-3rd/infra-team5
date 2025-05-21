package com.team5.surbee.dto.request;

import java.util.List;

public record AnswerRequest(
        Integer questionId,
        String answerText,
        List<Integer> selectedOptionIds
) {
}
