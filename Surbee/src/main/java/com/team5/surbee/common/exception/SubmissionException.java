package com.team5.surbee.common.exception;

public class SubmissionException extends CustomException {
    public SubmissionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
