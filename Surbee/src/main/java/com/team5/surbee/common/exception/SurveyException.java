package com.team5.surbee.common.exception;

public class SurveyException extends CustomException {
    public SurveyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
