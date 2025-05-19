package com.team5.surbee.common.exception;

public class AnswerException extends CustomException {
    public AnswerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
