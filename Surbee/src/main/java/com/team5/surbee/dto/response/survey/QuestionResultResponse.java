package com.team5.surbee.dto.response.survey;

import com.team5.surbee.entity.constant.QuestionType;

import java.util.List;

public record QuestionResultResponse(
        Integer id,                         // 질문 ID
        String questionText,                // 질문 내용
        QuestionType questionType,          // 질문 유형 (MULTIPLE_CHOICE, CHECKBOX, SHORT_ANSWER, LONG_ANSWER)
        Long count,                         // 질문 응답 수
        List<OptionResultResponse> options, // 객관식일 경우에만 사용
        List<String> answers                // 주관식일 경우에만 사용
) {
}
