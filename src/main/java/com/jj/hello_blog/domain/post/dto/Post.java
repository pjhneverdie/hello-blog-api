package com.jj.hello_blog.domain.post.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Post {

    private final Integer id;

    private final String title;

    private final String content;

    private final String thumbUrl;

    private final int categoryId;

    private final LocalDateTime fixedAt;

    private final LocalDateTime createdAt;

}
