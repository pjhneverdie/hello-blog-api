package com.jj.hello_blog.domain.post.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private int id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime fixedAt;
    private int categoryId;
    private int likeCount;
    private int commentCount;
}
