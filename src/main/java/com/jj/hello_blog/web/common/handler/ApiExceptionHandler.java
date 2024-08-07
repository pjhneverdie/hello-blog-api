package com.jj.hello_blog.web.common.handler;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.common.exception.GlobalExceptionCode;
import com.jj.hello_blog.web.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleServerException() {
        ApiResponse<Void> response = new ApiResponse<>(null);

        response.setMessage(GlobalExceptionCode.INTERNAL_SERVER_ERROR.message());

        return new ResponseEntity<>(response, GlobalExceptionCode.INTERNAL_SERVER_ERROR.httpStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException() {
        ApiResponse<Void> response = new ApiResponse<>(null);

        response.setMessage(GlobalExceptionCode.NOT_FOUND.message());

        return new ResponseEntity<>(response, GlobalExceptionCode.NOT_FOUND.httpStatus());
    }

    @ExceptionHandler({HttpMessageConversionException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse<Void>> handleValidationException() {
        ApiResponse<Void> response = new ApiResponse<>(null);

        response.setMessage(GlobalExceptionCode.BAD_REQUEST.message());

        return new ResponseEntity<>(response, GlobalExceptionCode.BAD_REQUEST.httpStatus());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException customException) {
        ApiResponse<Void> response = new ApiResponse<>(null);

        response.setMessage(customException.getExceptionCode().message());

        return new ResponseEntity<>(response, customException.getExceptionCode().httpStatus());
    }

}