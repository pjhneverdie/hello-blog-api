package com.jj.hello_blog.domain.category.exception;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.jj.hello_blog.domain.common.exception.ExceptionCode;

@RequiredArgsConstructor
public enum CategoryExceptionCode implements ExceptionCode {

    // 이미 존재하는 카테고리를 또 추가 하려는 경우
    DUPLICATED_CATEGORY("DUPLICATED_CATEGORY", HttpStatus.BAD_REQUEST),

    // 존재하지 않는 카테고리 조회 시
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", HttpStatus.BAD_REQUEST),

    // 부모 카테고리에서 자식 카테고리로 이동하려는 경우
    INVALID_CATEGORY_HIERARCHY("INVALID_CATEGORY_HIERARCHY", HttpStatus.BAD_REQUEST),

    // 게시글이 있는 카테고리를 지우려고 하는 경우
    POSTS_EXIST_YET("POSTS_EXIST_YET", HttpStatus.BAD_REQUEST);

    @Override
    public String code() {
        return this.code;
    }

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    private final String code;

    private final HttpStatus httpStatus;

}
