package com.team5.surbee.common.exception;

public class UserExcpetion extends CustomException{
    public UserExcpetion(ErrorCode errorCode){
        super(errorCode);
    }
}
