package com.jj.hello_blog.domain.member.exception;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;

@RequiredArgsConstructor
public enum MemberExceptionCode implements ExceptionCode {
    // 이메일 or 비밀번호가 일치하지 않을 시
    SIGN_IN_FAILED(HttpStatus.BAD_REQUEST, "SIGN_IN_FAILED"),

    // 이미 가입된 이메일로 가입 요청을 보낼 시
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "DUPLICATED_EMAIL");

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
