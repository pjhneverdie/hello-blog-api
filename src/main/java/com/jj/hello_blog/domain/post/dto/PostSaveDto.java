package com.jj.hello_blog.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostSaveDto {
    private final String title;
    private final String content;
    private final int categoryId;
}
