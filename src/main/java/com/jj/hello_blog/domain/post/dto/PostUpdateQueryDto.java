package com.jj.hello_blog.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateQueryDto {

    private final int id;

    private final String title;

    private final String content;

    private final String thumbUrl;

    private final int categoryId;

}
