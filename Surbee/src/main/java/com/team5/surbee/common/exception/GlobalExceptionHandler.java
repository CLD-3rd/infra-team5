package com.team5.surbee.common.exception;

import com.team5.surbee.dto.response.ErrorResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final String ERROR_PAGE = "error/error";

    @ExceptionHandler(CustomException.class)
    public String handleCustom(CustomException e, Model model) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        model.addAttribute("error", errorResponse);
        return ERROR_PAGE; // templates/error/error.html
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        model.addAttribute("error", errorResponse);
        return ERROR_PAGE;
    }
}
