package com.searc.kamil.booksearch.exception;

import com.searc.kamil.booksearch.model.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse handleNotFoundException(RuntimeException ex) {
        ApiResponse apiResponse = new ApiResponse(404, "Not Found Book", null);
        return apiResponse;
    }

}