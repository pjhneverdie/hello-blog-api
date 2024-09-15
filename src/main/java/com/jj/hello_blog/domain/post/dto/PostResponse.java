package com.jj.hello_blog.domain.post.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private int id;

    private String title;

    private String content;

    private String thumbUrl;

    private int categoryId;

    private LocalDateTime fixedAt;

    private LocalDateTime createdAt;

}
