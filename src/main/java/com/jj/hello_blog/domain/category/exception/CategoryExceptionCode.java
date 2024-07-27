package com.jj.hello_blog.domain.category.exception;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CategoryExceptionCode implements ExceptionCode {

    // 이메일 or 비밀번호가 일치하지 않을 시
    DUPLICATED_CATEGORY(HttpStatus.OK, "DUPLICATED_CATEGORY");

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
