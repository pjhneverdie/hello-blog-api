package com.jj.hello_blog.domain.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum GlobalExceptionCode implements ExceptionCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR");

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String message() {
        return this.processingCode;
    }

    private final HttpStatus httpStatus;

    private final String processingCode;

}
