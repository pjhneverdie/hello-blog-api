package com.jj.hello_blog.domain.category.exception;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;

@RequiredArgsConstructor
public enum CategoryExceptionCode implements ExceptionCode {

    // 이미 존재하는 카테고리를 또 추가 하려는 경우
    DUPLICATED_CATEGORY(HttpStatus.BAD_REQUEST, "DUPLICATED_CATEGORY");

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String code() {
        return this.code;
    }

    private final HttpStatus httpStatus;

    private final String code;
}
