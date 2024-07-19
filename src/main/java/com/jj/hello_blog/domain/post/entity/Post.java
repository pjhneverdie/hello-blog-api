package com.jj.hello_blog.domain.post.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Post {
    private final Integer id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime fixedAt;
    private final int categoryId;

    public Post(Integer id, String title, String content, LocalDateTime createdAt, LocalDateTime fixedAt, int categoryId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.fixedAt = fixedAt;
        this.categoryId = categoryId;
    }
}
