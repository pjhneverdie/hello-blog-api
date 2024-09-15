package com.jj.hello_blog.domain.post.dto;

import lombok.Getter;

@Getter
public class PostPaginationCond {

    private final int offset;

    private final int limit = 15;

    public PostPaginationCond(int page) {
        this.offset = (page - 1) * limit;
    }

}
