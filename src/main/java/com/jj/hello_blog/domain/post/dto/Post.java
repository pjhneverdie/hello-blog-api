package com.jj.hello_blog.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Post {
    private final Integer id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime fixedAt;
    private final int categoryId;
}
