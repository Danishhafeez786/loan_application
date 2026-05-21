package com.loanapproval.handler;

import com.loanapproval.model.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return ApiResponse.builder()
                .success(false)
                .message(message)
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {

        return ApiResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .build();
    }
}
