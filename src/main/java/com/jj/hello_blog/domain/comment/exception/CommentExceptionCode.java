package com.jj.hello_blog.domain.comment.exception;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CommentExceptionCode implements ExceptionCode {
    // 자기 댓글이 아닌데 삭제 혹은 수정 하려는 경우
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED");

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String code() {
        return this.message;
    }

    private final HttpStatus httpStatus;

    private final String message;
}
