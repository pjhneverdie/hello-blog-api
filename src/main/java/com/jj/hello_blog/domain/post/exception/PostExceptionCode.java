package com.jj.hello_blog.domain.post.exception;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;

@RequiredArgsConstructor
public enum PostExceptionCode implements ExceptionCode {

    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", HttpStatus.BAD_REQUEST),

    POST_NOT_FOUND("POST_NOT_FOUND", HttpStatus.BAD_REQUEST);

    @Override
    public String code() {
        return code;
    }

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    private final String code;

    private final HttpStatus httpStatus;

}
