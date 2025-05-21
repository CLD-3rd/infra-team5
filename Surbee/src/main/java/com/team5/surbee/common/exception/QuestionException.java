package com.team5.surbee.common.exception;

import org.springframework.http.HttpStatus;

public class QuestionException extends CustomException{
    public QuestionException(ErrorCode errorCode){
        super(errorCode);
    }
}
