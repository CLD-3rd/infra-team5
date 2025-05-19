package com.team5.surbee.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 🔐 User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "U002", "권한이 없습니다."),

    // 📋 Survey
    SURVEY_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "설문을 찾을 수 없습니다."),
    SURVEY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "S002", "설문에 접근할 수 없습니다."),

    // ❓ Question
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "Q001", "질문을 찾을 수 없습니다."),

    // 🗳 Option
    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "옵션을 찾을 수 없습니다."),

    // 📝 Submission
    SUBMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "SB001", "응답을 찾을 수 없습니다."),
    SUBMISSION_DUPLICATE(HttpStatus.CONFLICT, "SB002", "이미 응답을 제출했습니다."),

    // ✅ Answer
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "답변을 찾을 수 없습니다."),

    // 🧱 공통
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "유효하지 않은 입력입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}