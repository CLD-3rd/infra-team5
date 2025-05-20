package com.team5.surbee.dto.response.survey;

public record OptionResultResponse(
        Integer optionId,              // 옵션 ID
        String optionText,             // 옵션 내용
        Long count,                    // 해당 옵션 응답 수
        Double rate                    // 전체 중 해당 옵션 선택 비율 (퍼센트)
) {
}
