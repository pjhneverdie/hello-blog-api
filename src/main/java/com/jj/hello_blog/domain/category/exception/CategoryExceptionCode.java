package com.jj.hello_blog.domain.category.exception;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CategoryExceptionCode implements ExceptionCode {

    // 이미 존재하는 카테고리를 또 추가 하려는 경우
    DUPLICATED_CATEGORY(HttpStatus.SERVICE_UNAVAILABLE, "DUPLICATED_CATEGORY");

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String message() {
        return this.message;
    }

    private final HttpStatus httpStatus;

    private final String message;
}
