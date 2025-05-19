package com.team5.surbee.common.exception;

public class OptionException extends CustomException {
    public OptionException(ErrorCode errorCode) {
        super(errorCode);
    }
}