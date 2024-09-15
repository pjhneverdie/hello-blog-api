package com.jj.hello_blog.web.common.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.common.exception.GlobalExceptionCode;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleServerException(Exception e) {
        ApiResponse<Void> apiResponse = createApiResponse();

        apiResponse.setExceptionCode(GlobalExceptionCode.INTERNAL_SERVER_ERROR.code());

        return new ResponseEntity<>(apiResponse, GlobalExceptionCode.INTERNAL_SERVER_ERROR.httpStatus());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException() {
        ApiResponse<Void> apiResponse = createApiResponse();

        apiResponse.setExceptionCode(GlobalExceptionCode.NOT_FOUND.code());

        return new ResponseEntity<>(apiResponse, GlobalExceptionCode.NOT_FOUND.httpStatus());
    }

    @ExceptionHandler({HttpMessageConversionException.class, MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
    public ResponseEntity<ApiResponse<Void>> handleValidationException(Exception e) {
        ApiResponse<Void> apiResponse = createApiResponse();

        apiResponse.setExceptionCode(GlobalExceptionCode.BAD_REQUEST.code());

        return new ResponseEntity<>(apiResponse, GlobalExceptionCode.BAD_REQUEST.httpStatus());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException customException) {
        ApiResponse<Void> apiResponse = createApiResponse();

        apiResponse.setExceptionCode(customException.getExceptionCode().code());

        return new ResponseEntity<>(apiResponse, customException.getExceptionCode().httpStatus());
    }

    private ApiResponse<Void> createApiResponse() {
        return new ApiResponse<>(null);
    }

}
