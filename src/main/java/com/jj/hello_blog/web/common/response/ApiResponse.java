package com.jj.hello_blog.web.common.response;


import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiResponse<T> {

    @Setter
    private String message = "ok";

    private final T value;

    public ApiResponse(T value) {
        this.value = value;
    }

}
