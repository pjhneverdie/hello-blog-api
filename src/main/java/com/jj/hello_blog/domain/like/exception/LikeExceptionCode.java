package com.jj.hello_blog.domain.like.exception;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum LikeExceptionCode implements ExceptionCode {
    // 좋아요 중복 시
    DUPLICATED_LIKE(HttpStatus.SERVICE_UNAVAILABLE, "DUPLICATED_LIKE");

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
