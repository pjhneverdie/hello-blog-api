package com.jj.hello_blog.domain.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionCode {

    String code();

    HttpStatus httpStatus();

}
