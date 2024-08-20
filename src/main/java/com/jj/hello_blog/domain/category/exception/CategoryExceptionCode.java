package com.jj.hello_blog.domain.category.exception;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;

@RequiredArgsConstructor
public enum CategoryExceptionCode implements ExceptionCode {

    // 이미 존재하는 카테고리를 또 추가 하려는 경우
    DUPLICATED_CATEGORY(HttpStatus.BAD_REQUEST, "DUPLICATED_CATEGORY"),

    // 존재하지 않는 카테고리 조회 시
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY_NOT_FOUND"),

    // 부모 카테고리에서 자식 카테고리로 이동하려는 경우
    INVALID_CATEGORY_HIERARCHY(HttpStatus.BAD_REQUEST, "INVALID_CATEGORY_HIERARCHY");

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
